package com.assignment.liveroomInvite.vo;

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
@Table(name = "CC_LIVE_ROOM_INVITE")
public class LiveRoomInviteVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "live_room_invite_seq_gen")
    @SequenceGenerator(name = "live_room_invite_seq_gen", sequenceName = "SEQ_CC_LIVEROOM_INVITE", allocationSize = 1)
    @Column(name = "INVITE_NO")
    private Long inviteNo; // ID는 보통 Long 타입을 사용합니다.

    @Column(name = "ROOM_ID", nullable = false)
    private Long roomId;

    @Column(name = "INVITER_ID", nullable = false)
    private String inviterId;

    @Column(name = "INVITEE_ID", nullable = false)
    private String inviteeId;

    @Column(name = "STATUS", length = 1)
    private String status;

    @CreationTimestamp // INSERT 시 자동으로 현재 시간이 입력됩니다.
    @Column(name = "CREATE_DATE", updatable = false)
    private LocalDateTime createDate; // 다른 VO들과 통일성을 위해 LocalDateTime 사용

	public Long getInviteNo() {
		return inviteNo;
	}

	public void setInviteNo(Long inviteNo) {
		this.inviteNo = inviteNo;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public String getInviterId() {
		return inviterId;
	}

	public void setInviterId(String inviterId) {
		this.inviterId = inviterId;
	}

	public String getInviteeId() {
		return inviteeId;
	}

	public void setInviteeId(String inviteeId) {
		this.inviteeId = inviteeId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}
    
    
}