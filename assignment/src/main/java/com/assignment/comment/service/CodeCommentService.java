package com.assignment.comment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.comment.dao.CodeCommentRepository;
import com.assignment.comment.vo.CodeCommentVO;

@Service
public class CodeCommentService {
	@Autowired
	private CodeCommentRepository codeCommentRepository;

	public List<CodeCommentVO> getCommentsByBoardNo(Integer boardNo) {
		return codeCommentRepository.findByBoardNoOrderByLineNumberAsc(boardNo);
	}

	public CodeCommentVO addComment(CodeCommentVO comment) {
		return codeCommentRepository.save(comment);
	}

}
