package com.assignment.milestone.vo;

import java.time.LocalDate;
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
@Table(name = "CC_TASK")
public class TaskVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_seq_generator")
	@SequenceGenerator(name = "task_seq_generator", sequenceName = "SEQ_CC_TASK", allocationSize = 1)
	@Column(name = "TASK_ID")
	private Integer taskId;

	@Column(name = "MILESTONE_ID", nullable = false)
	private Integer milestoneId;

	@Column(name = "ASSIGNEE_ID")
	private String assigneeId;

	@Column(name = "TITLE", nullable = false, length = 200)
	private String title;

	@Column(name = "STATUS", nullable = false, length = 20)
	private String status = "TODO";

	@CreationTimestamp
	@Column(name = "CREATE_DATE", updatable = false)
	private LocalDateTime createDate;

	@Column(name = "START_DATE")
	private LocalDate startDate;

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	@Column(name = "END_DATE")
	private LocalDate endDate;

	// --- Getters and Setters ---

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public Integer getMilestoneId() {
		return milestoneId;
	}

	public void setMilestoneId(Integer milestoneId) {
		this.milestoneId = milestoneId;
	}

	public String getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(String assigneeId) {
		this.assigneeId = assigneeId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
