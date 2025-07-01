package com.assignment.like.controller;

import com.assignment.like.service.LikeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController // 이 컨트롤러의 모든 메소드는 @ResponseBody가 적용된 것처럼 동작합니다.
@RequestMapping("/api/boards/{boardNo}/like")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping
    public ResponseEntity<?> toggleLike(@PathVariable("boardNo") int boardNo, HttpSession session) {
        String memberId = (String) session.getAttribute("loginId");
        if (memberId == null) {
            // 로그인하지 않은 사용자는 401 Unauthorized 에러를 반환
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        try {
            Map<String, Object> result = likeService.toggleLike(boardNo, memberId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            // 서버 내부 오류 발생 시 500 Internal Server Error 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("오류가 발생했습니다.");
        }
    }
    
    @GetMapping("/status") // -> /api/boards/{boardNo}/like/status 경로에 매핑됩니다.
    public ResponseEntity<?> getLikeStatus(@PathVariable("boardNo") int boardNo, HttpSession session) {
        String memberId = (String) session.getAttribute("loginId");
        if (memberId == null) {
            // 비로그인 사용자는 '좋아요 안 누른 상태'로 간주하고, 좋아요 수만 반환
            long likeCount =  (Long)likeService.getLikeStatus(boardNo, "").get("likeCount");
            Map<String, Object> result = new HashMap<>();
            result.put("liked", false);
            result.put("likeCount", likeCount);
            return ResponseEntity.ok(result);
        }

        Map<String, Object> result = likeService.getLikeStatus(boardNo, memberId);
        return ResponseEntity.ok(result);
    }
    
    
}