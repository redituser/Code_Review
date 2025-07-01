package com.assignment.group.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.group.vo.GroupVO;


@Repository
public interface GroupRepository extends JpaRepository<GroupVO, Integer> {

	List<GroupVO> findByCreatorId(String creatorId);
	boolean existsByGroupName(String groupName);
	GroupVO findByGroupName(String groupName);
	
	
}
