package com.assignment.liveroom.vo;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "CC_LIVE_ROOM")
public class LiveRoomVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "live_room_seq_generator")
	@SequenceGenerator(name = "live_room_seq_generator", sequenceName = "SEQ_CC_LIVE_ROOM", allocationSize = 1)
	@Column(name = "ROOM_ID")
	private Long roomId;

	@Column(name = "ROOM_NAME", nullable = false)
	private String roomName;

	@Column(name = "CREATOR_ID", nullable = false)
	private String creatorId;

	@Column(name = "GROUP_NO")
	private Integer groupNo;

	@Lob // CLOB 타입과 매핑하기 위해 사용
	@Column(name = "CURRENT_CODE")
	private String currentCode;

	@CreationTimestamp
	@Column(name = "CREATE_DATE", updatable = false)
	private LocalDateTime createDate;

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public Integer getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(Integer groupNo) {
		this.groupNo = groupNo;
	}

	public String getCurrentCode() {
		return currentCode;
	}

	public void setCurrentCode(String currentCode) {
		this.currentCode = currentCode;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	
	
}
