package com.assignment.board.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.assignment.like.vo.Like;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "CC_BOARD")
public class BoardVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_seq_generator")
	@SequenceGenerator(name = "board_seq_generator", sequenceName = "SEQ_CC_BOARD_NO", // CC_BOARD 테이블의 시퀀스 이름
			allocationSize = 1 // 이전에 해결했던 allocationSize 문제 방지
	)
	@Column(name = "BOARD_NO")
	private Integer boardNo;

	@Column(name = "BOARD_TITLE")
	private String boardTitle;

	@Column(name = "BOARD_CONTENT")
	private String boardContent;

	@Column(name = "BOARD_CATEGORY") // 이 부분이 중요!
	private String boardCategory;

	@Column(name = "WRITER_ID")
	private String writerId;

	@Column(name = "GROUP_NO")
	private Integer groupNo;

	@Column(name = "PASS_STATUS")
	private String passStatus;

	@Transient // 이 필드는 데이터베이스의 컬럼과 매핑되지 않음을 명시
	private long likeCount;
	
	@OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
	private List<Like> likes;
	
	

	@CreationTimestamp
	@Column(name = "CREATE_DATE", updatable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.S")
	private Date createDate;

	@UpdateTimestamp
	@Column(name = "UPDATE_DATE")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.S")
	private Date updateDate;
	
	
    // 1. 부모 글(질문) 번호
    @Column(name = "PARENT_BOARD_NO")
    private Integer parentBoardNo;

    // 2. 채택된 답변 번호
    @Column(name = "ACCEPTED_ANSWER_NO")
    private Integer acceptedAnswerNo;

    // 3. (JPA 관계 매핑) 부모-자식 관계 설정 (Self-Join)
    // 질문(부모) 하나에 여러 답변(자식)이 달리는 구조
    @ManyToOne
    @JoinColumn(name = "PARENT_BOARD_NO", referencedColumnName = "BOARD_NO", insertable = false, updatable = false)
    private BoardVO parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<BoardVO> answers = new ArrayList<>();


    @Transient // 이 답변이 채택되었는지 여부를 UI에 전달하기 위함
    private boolean isAccepted = false;



    
	

	public boolean isAccepted() {
		return isAccepted;
	}

	public void setAccepted(boolean isAccepted) {
		this.isAccepted = isAccepted;
	}

	public List<Like> getLikes() {
		return likes;
	}

	public void setLikes(List<Like> likes) {
		this.likes = likes;
	}

	public Integer getParentBoardNo() {
		return parentBoardNo;
	}

	public void setParentBoardNo(Integer parentBoardNo) {
		this.parentBoardNo = parentBoardNo;
	}

	public Integer getAcceptedAnswerNo() {
		return acceptedAnswerNo;
	}

	public void setAcceptedAnswerNo(Integer acceptedAnswerNo) {
		this.acceptedAnswerNo = acceptedAnswerNo;
	}

	public BoardVO getParent() {
		return parent;
	}

	public void setParent(BoardVO parent) {
		this.parent = parent;
	}

	public List<BoardVO> getAnswers() {
		return answers;
	}

	public void setAnswers(List<BoardVO> answers) {
		this.answers = answers;
	}

	public long getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(long likeCount) {
		this.likeCount = likeCount;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	@Column(name = "BOARD_TYPE", nullable = false)
	private String boardType = "PUBLIC";

	public String getBoardType() {
		return boardType;
	}

	public void setBoardType(String boardType) {
		this.boardType = boardType;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public int getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(Integer boardNo) {
		this.boardNo = boardNo;
	}

	public String getBoardTitle() {
		return boardTitle;
	}

	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}

	public String getBoardContent() {
		return boardContent;
	}

	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}

	public String getBoardCategory() {
		return boardCategory;
	}

	public void setBoardCategory(String boardCategory) {
		this.boardCategory = boardCategory;
	}

	public String getWriterId() {
		return writerId;
	}

	public void setWriterId(String writerId) {
		this.writerId = writerId;
	}

	public Integer getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(Integer groupNo) {
		this.groupNo = groupNo;
	}

	public String getPassStatus() {
		return passStatus;
	}

	public void setPassStatus(String passStatus) {
		this.passStatus = passStatus;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
