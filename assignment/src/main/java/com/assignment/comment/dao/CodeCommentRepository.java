package com.assignment.comment.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.assignment.comment.vo.CodeCommentVO;
import java.util.List;

public interface CodeCommentRepository extends JpaRepository<CodeCommentVO, Integer> {

	// 게시글 번호(boardNo)로 모든 댓글을 찾아 라인 번호(lineNumber) 오름차순으로 정렬
	List<CodeCommentVO> findByBoardNoOrderByLineNumberAsc(Integer boardNo);
}
