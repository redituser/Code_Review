package com.assignment.comment.vo;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "CC_CODE_COMMENT")
public class CodeCommentVO {

	 @Id
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cc_comment_seq_generator")
	    @SequenceGenerator(
	        name = "cc_comment_seq_generator",
	        sequenceName = "SEQ_CC_COMMENT_NO", // DB 시퀀스 이름
	        allocationSize = 1                  // 이 값이 1인지 확인
	    )
	private Integer commentNo;

	@Column(nullable = false)
	private Integer boardNo;

	@Column(nullable = false)
	private String memberId;

	@Column(nullable = false)
	private Integer lineNumber;

	@Column(nullable = false, length = 1000)
	private String commentContent;

	 @CreationTimestamp
	private LocalDateTime createDate;

	public Integer getCommentNo() {
		return commentNo;
	}

	public void setCommentNo(Integer commentNo) {
		this.commentNo = commentNo;
	}

	public Integer getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(Integer boardNo) {
		this.boardNo = boardNo;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Integer getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(Integer lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

}
