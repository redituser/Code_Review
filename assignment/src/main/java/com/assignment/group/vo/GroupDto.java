package com.assignment.group.vo;

import java.sql.Date;

public class GroupDto {
	private Integer groupNo;
	private String groupName;
	private String creatorId;
	private Date createDate;
	private Date deleteDate;
	private String memberType; // 추가 내 그룹 조회시 한번에 Db에받아오기 위함

	// mustache
	private boolean isLeader;

	// 그룹 이름 첫 글자를 저장할 필드 추가
	private String groupInitial;

	
	//그룹의 첫글자를 보냄 에러시 ? 보냄
	public String getGroupInitial() {
		return groupInitial;
	}

	
	
	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;

		if ("L".equals(memberType)) {
			this.isLeader = true;
		}
	}

	public boolean isLeader() {
		return isLeader;
	}

	public void setLeader(boolean isLeader) {
		this.isLeader = isLeader;
	}

	public GroupDto() {
	}

	public Integer getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(Integer groupNo) {
		this.groupNo = groupNo;
	}

	public String getGroupName() {
		return groupName;
	}

	// groupName이 세팅될 때, 첫 글자(groupInitial)도 함께 세팅되도록 로직 추가
	public void setGroupName(String groupName) {
		this.groupName = groupName;
		if (groupName != null && !groupName.isEmpty()) {
			this.groupInitial = groupName.substring(0, 1).toUpperCase();
		} else {
			this.groupInitial = "?";
		}
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getDeleteDate() {
		return deleteDate;
	}

	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}

	@Override
	public String toString() {
		return "GroupDto{" + "groupNo=" + groupNo + ", groupName='" + groupName + '\'' + ", creatorId='" + creatorId
				+ '\'' + ", createDate=" + createDate + ", deleteDate=" + deleteDate + '}';
	}
}