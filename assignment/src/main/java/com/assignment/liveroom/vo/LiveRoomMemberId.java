package com.assignment.liveroom.vo;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class LiveRoomMemberId implements Serializable {
	@Column(name = "ROOM_ID")
	private Long roomId;
	
	@Column(name = "MEMBER_ID")
	private String memberId;

	public LiveRoomMemberId(Long roomId2, String inviteeId) {
		// TODO Auto-generated constructor stub
	}

	public LiveRoomMemberId() {
		// TODO Auto-generated constructor stub
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	
	
	

}
