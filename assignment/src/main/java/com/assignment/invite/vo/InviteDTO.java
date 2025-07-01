package com.assignment.invite.vo;

import java.util.Date;

public class InviteDTO {
	private int inviteNo;
	private int groupNo;
	private String inviterId;
	private String inviteeId;
	private String status; // W, A, R
	private String groupName;
	private String inviteType;
	private String targetName;
	private Long targetId;

	public String getInviteType() {
		return inviteType;
	}

	public void setInviteType(String inviteType) {
		this.inviteType = inviteType;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getInviteNo() {
		return inviteNo;
	}

	public void setInviteNo(int inviteNo) {
		this.inviteNo = inviteNo;
	}

	public int getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(int groupNo) {
		this.groupNo = groupNo;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	private Date createDate;
}
