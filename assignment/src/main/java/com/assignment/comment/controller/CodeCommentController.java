package com.assignment.comment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.comment.service.CodeCommentService;
import com.assignment.comment.vo.CodeCommentVO;

import jakarta.servlet.http.HttpSession;

@RestController
public class CodeCommentController {

    @Autowired
    CodeCommentService codeCommentService;

    @GetMapping("/api/board/{boardNo}/code-comments")
    public List<CodeCommentVO> getComments(@PathVariable("boardNo") int boardNo) {
        System.out.println("codeCommentController = 코드 라인 댓글 호출");
        return codeCommentService.getCommentsByBoardNo(boardNo);
    }

    @PostMapping("/api/board/{boardNo}/code-comments")
    public CodeCommentVO addComment(@PathVariable("boardNo") Integer boardNo,
                                   @RequestBody CodeCommentVO comment,
                                   HttpSession session) {

        String loginId = (String) session.getAttribute("loginId");
        if (loginId == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        comment.setBoardNo(boardNo);
        comment.setMemberId(loginId);
        return codeCommentService.addComment(comment);
    }
}