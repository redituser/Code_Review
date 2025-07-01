package com.assignment.milestone.vo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "CC_MILESTONE")
public class MilestoneVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "milestone_seq_generator")
	@SequenceGenerator(name = "milestone_seq_generator", sequenceName = "SEQ_CC_MILESTONE", allocationSize = 1)
	@Column(name = "MILESTONE_ID")
	private Integer milestoneId;

	@Column(name = "GROUP_NO", nullable = false)
	private Integer groupNo;

	@Column(name = "TITLE", nullable = false, length = 200)
	private String title;

	@Column(name = "DESCRIPTION", length = 2000)
	private String description;

	@Column(name = "STATUS", nullable = false, length = 20)
	private String status = "OPEN";

	@JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "DUE_DATE")
	private LocalDate dueDate;

	@CreationTimestamp
	@Column(name = "CREATE_DATE", updatable = false)
	private LocalDateTime createDate;

	// --- Getters and Setters ---

	public Integer getMilestoneId() {
		return milestoneId;
	}

	public void setMilestoneId(Integer milestoneId) {
		this.milestoneId = milestoneId;
	}

	public Integer getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(Integer groupNo) {
		this.groupNo = groupNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

}
