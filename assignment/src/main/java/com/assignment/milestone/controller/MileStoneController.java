package com.assignment.milestone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.milestone.service.MilestoneService;
import com.assignment.milestone.vo.MilestoneVO;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/groups/{groupNo}/milestones")
public class MileStoneController {

	@Autowired
	private MilestoneService milestoneService;

	@GetMapping
	public ResponseEntity<List<MilestoneVO>> getMilestones(@PathVariable("groupNo") Integer groupNo) {
		List<MilestoneVO> milestones = milestoneService.getMilestonesByGroup(groupNo);
		return ResponseEntity.ok(milestones); // ??
	}

	@PostMapping
	public ResponseEntity<MilestoneVO> createMilestone(@PathVariable("groupNo") Integer groupNo,
			@RequestBody MilestoneVO milestone, HttpSession session) {
		String memberId = (String) session.getAttribute("loginId");
		if (memberId == null) {
			return ResponseEntity.status(401).build(); // 401 에러
		}

		milestone.setGroupNo(groupNo); // 경로 변수에서 받은 groupNo를 VO에 설정
		MilestoneVO createdMilestone = milestoneService.createMilestone(milestone, memberId);
		return ResponseEntity.ok(createdMilestone);
	}

	// 특정 마일스톤 정보 업데이트
	@PutMapping("/{milestoneId}")
	public ResponseEntity<MilestoneVO> updateMilestone(@PathVariable("groupNo") Integer groupNo, @PathVariable("milestoneId") Integer milestoneId,
			@RequestBody MilestoneVO milestoneDetails) {
		// 권한 검사 로직을 서비스에 추가할 수 있습니다. (예: 그룹 멤버만 수정 가능)
		MilestoneVO updatedMilestone = milestoneService.updateMilestone(milestoneId, milestoneDetails);
		return ResponseEntity.ok(updatedMilestone);
	}

	// 마일스톤 삭제
	@DeleteMapping("/{milestoneId}")
	public ResponseEntity<Void> deleteMilestone(@PathVariable("groupNo") Integer groupNo, @PathVariable("milestoneId") Integer milestoneId) {
		// 권한 검사 로직을 서비스에 추가할 수 있습니다. (예: 그룹 리더만 삭제 가능)
		milestoneService.deleteMilestone(milestoneId);
		return ResponseEntity.noContent().build(); // 204 No Content ??? 
	}
}
