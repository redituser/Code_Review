package com.assignment.comment.controller;

import com.assignment.comment.service.CommentService;
import com.assignment.comment.vo.CommentVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api") // ★ 공통 경로를 /api로 변경
public class CommentController {

    @Autowired
    private CommentService commentService;

    // 댓글 목록 조회: GET /api/board/{boardNo}/comments
    @GetMapping("/board/{boardNo}/comments")
    public ResponseEntity<List<CommentVO>> getCommentsByBoardNo(@PathVariable("boardNo") Integer boardNo) {
        List<CommentVO> comments = commentService.getCommentsByBoardNo(boardNo);
        return ResponseEntity.ok(comments);
    }

    // 새 댓글 작성: POST /api/board/{boardNo}/comments
    @PostMapping("/board/{boardNo}/comments")
    public ResponseEntity<?> addComment(@PathVariable("boardNo") Integer boardNo,
                                        @RequestBody CommentVO comment,
                                        HttpSession session) {
        String loginId = (String) session.getAttribute("loginId");
        if (loginId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        comment.setBoardNo(boardNo);
        comment.setWriterId(loginId);
        CommentVO savedComment = commentService.addComment(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
    }

    // ★★★ [추가] 댓글 수정 API ★★★
    // PUT /api/comments/{commentNo}
    @PutMapping("/comments/{commentNo}")
    public ResponseEntity<?> updateComment(@PathVariable("commentNo") Integer commentNo,
                                           @RequestBody Map<String, String> payload, // JSON {"commentContent": "..."}
                                           HttpSession session) {
        String loginId = (String) session.getAttribute("loginId");
        if (loginId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        try {
            String newContent = payload.get("commentContent");
            CommentVO updatedComment = commentService.updateComment(commentNo, newContent, loginId);
            return ResponseEntity.ok(updatedComment);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // ★★★ [추가] 댓글 삭제 API ★★★
    // DELETE /api/comments/{commentNo}
    @DeleteMapping("/comments/{commentNo}")
    public ResponseEntity<?> deleteComment(@PathVariable("commentNo") Integer commentNo,
                                           HttpSession session) {
        String loginId = (String) session.getAttribute("loginId");
        if (loginId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        try {
            commentService.deleteComment(commentNo, loginId);
            return ResponseEntity.ok().body(Map.of("message", "댓글이 삭제되었습니다."));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}