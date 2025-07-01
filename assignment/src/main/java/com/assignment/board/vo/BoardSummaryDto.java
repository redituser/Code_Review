package com.assignment.board.vo;

import java.time.LocalDateTime;
import java.util.Date;

public class BoardSummaryDto {
	   private int boardNo;
	    private String boardTitle;
	    private String writerId;
	    private Date createDate; // 날짜 포맷팅을 위해 LocalDateTime 사용
	    private String boardCategory;
	    private long likeCount;       // 좋아요 수
	    private boolean isAccepted;   // 답변 채택 여부
	    private Integer groupNo;
	    
	    
	    // 기본 생성자
	    public BoardSummaryDto() {}

	    // 생성자
	    public BoardSummaryDto(BoardVO board, long likeCount) {
	        this.boardNo = board.getBoardNo();
	        this.boardTitle = board.getBoardTitle();
	        this.writerId = board.getWriterId();
	        this.createDate = board.getCreateDate(); // 이제 타입이 일치합니다.
	        this.boardCategory = board.getBoardCategory();
	        this.likeCount = likeCount;
	        this.isAccepted = (board.getAcceptedAnswerNo() != null);
	        this.groupNo = board.getGroupNo(); 
	    }

	    // Getters
	    public int getBoardNo() { return boardNo; }
	    public String getBoardTitle() { return boardTitle; }
	    public String getWriterId() { return writerId; }
	    public Date getCreateDate() { return createDate; }
	    public String getBoardCategory() { return boardCategory; }
	    public long getLikeCount() { return likeCount; }
	    public boolean isAccepted() { return isAccepted; }
	    public Integer getGroupNo() { return groupNo; } 
}
