package com.assignment.group.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "CC_GROUP")
public class GroupVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_seq")
	@SequenceGenerator(name = "group_seq", sequenceName = "SEQ_CC_GROUP", allocationSize = 1)
	@Column(name = "GROUP_NO")
	private Integer groupNo;

	@Column(name = "GROUP_NAME")
	private String groupName;

	@Column(name = "CREATOR_ID")
	private String creatorId;

	@Column(name = "CREATE_DATE")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.S")
	private Date createDate;

	@Column(name = "DELETE_DATE")
	private Date deleteDate; 
	
	
	public Date getDeleteDate() {
		return deleteDate;
	}

	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
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

	public void setGroupName(String groupName) {
		this.groupName = groupName;
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

	@Override
	public String toString() {
		return "GroupVO [groupNo=" + groupNo + ", groupName=" + groupName + ", creatorId=" + creatorId + ", createDate="
				+ createDate + "]";
	}

}
