package com.assignment.milestone.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignment.milestone.vo.MilestoneVO;

public interface MilestoneRepository extends JpaRepository<MilestoneVO, Integer>{
	
	
	List<MilestoneVO>findByGroupNoOrderByDueDateAsc(Integer groupNo);
	
	
}
