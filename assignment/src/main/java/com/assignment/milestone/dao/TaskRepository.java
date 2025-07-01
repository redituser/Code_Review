package com.assignment.milestone.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignment.milestone.vo.TaskVO;

public interface TaskRepository extends JpaRepository<TaskVO, Integer>{
	
	List<TaskVO> findByMilestoneIdOrderByCreateDateAsc(Integer milestoneId);
	
	List<TaskVO> findByAssigneeId(String assigneeId);
	
	
	
}
