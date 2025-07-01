package com.assignment.liveroom.vo;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "CC_LIVE_ROOM_MEMBER")
public class LiveRoomMemberVO {
	@EmbeddedId
	private LiveRoomMemberId id;

	@Column(name = "STATUS")
	private String status;

	@CreationTimestamp
	@Column(name = "JOIN_DATE", updatable = false)
	private LocalDateTime joinDate;

	public LiveRoomMemberId getId() {
		return id;
	}

	public void setId(LiveRoomMemberId id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(LocalDateTime joinDate) {
		this.joinDate = joinDate;
	}

}
