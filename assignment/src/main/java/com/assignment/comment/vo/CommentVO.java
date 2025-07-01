package com.assignment.comment.vo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "CC_COMMENT")
public class CommentVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cc_comment_seq_generator")
    @SequenceGenerator(
        name = "cc_comment_seq_generator",
        sequenceName = "SEQ_CC_COMMENT_NO",
        allocationSize = 1
    )
    private Integer commentNo;

    private Integer boardNo;
    private String writerId;
    private String commentContent;
    private Integer parentCommentNo;
   
    @Column(name="DEPTH")
    private Integer depth = 0;

    @CreationTimestamp //
    @Column(updatable = false) // 
    private LocalDateTime createDate;

    @UpdateTimestamp // 
    private LocalDateTime updateDate;
    
    @Transient
    private List<CommentVO> children = new ArrayList<>();
    
    // --- Getter and Setter ---

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

    public String getWriterId() {
        return writerId;
    }

    public void setWriterId(String writerId) {
        this.writerId = writerId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Integer getParentCommentNo() {
    	 System.out.println(">>> 최신 getParentCommentNo() 호출됨. 반환 타입: Integer"); 
        return parentCommentNo;
    }

    public List<CommentVO> getChildren() {
		return children;
	}

	public void setChildren(List<CommentVO> children) {
		this.children = children;
	}

	public void setParentCommentNo(Integer parentCommentNo) {
        this.parentCommentNo = parentCommentNo;
    }

    public Integer getDepth() {
    	System.out.println(">>> 최신 getDepth() 호출됨. 반환 타입: Integer");
        return depth;
    }

    public void setDepth(Integer depth) {
    	
        this.depth = depth;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }
}