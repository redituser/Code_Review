package com.assignment.comment.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignment.comment.vo.CommentVO;

public interface CommentRepository extends JpaRepository<CommentVO , Integer>{
	List<CommentVO> findByBoardNoOrderByCreateDateAsc(int board);
	List<CommentVO> findByBoardNoOrderByParentCommentNoAscCreateDateAsc(Integer boardNo);
	

	
	
}
