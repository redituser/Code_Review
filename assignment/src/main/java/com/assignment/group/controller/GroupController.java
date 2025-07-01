package com.assignment.group.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.assignment.board.service.BoardService;
import com.assignment.board.vo.BoardVO;
import com.assignment.group.Service.GroupService;
import com.assignment.group.vo.GroupDto;
import com.assignment.group.vo.GroupMemberDto;
import com.assignment.invite.service.InviteService;

import jakarta.servlet.http.HttpSession;

@Controller
public class GroupController {

	@Autowired
	GroupService service;

	@Autowired
	InviteService inviteService;

	@Autowired
	BoardService boardService;

	@GetMapping("/api/my-groups")
	@ResponseBody // 순수데이터 반환
	public List<GroupDto> getMyGroupList(HttpSession session) {
		String loginId = (String) session.getAttribute("loginId");
		if (loginId != null) {
			List<GroupDto> list = service.getMyGroups(loginId);
			System.out.println(list);
			return service.getMyGroups(loginId);

		}
		return Collections.emptyList();
	}

	@GetMapping("/api/group/{groupNo}/members")
	@ResponseBody
	public List<GroupMemberDto> getGroupMembers(@PathVariable("groupNo") int groupNo) {
		return service.selectMyGroupList(groupNo);
	}

	@PostMapping("/api/group/{groupNo}/delegate")
	@ResponseBody
	public Map<String, Object> delegateGroupLeader(@PathVariable("groupNo") int groupNo,
			@RequestParam("newLeaderId") String newLeaderId, HttpSession session) {

		String currentLeaderId = (String) session.getAttribute("loginId");
		Map<String, Object> result = new HashMap<>();

		if (currentLeaderId == null) {
			result.put("success", false);
			result.put("message", "로그인이 필요합니다.");
			return result;
		}

		try {
			boolean success = service.delegateLeader(groupNo, currentLeaderId, newLeaderId);
			if (success) {
				result.put("success", true);
				result.put("message", "리더가 성공적으로 위임되었습니다.");
			} else {
				result.put("success", false);
				result.put("message", "리더 위임에 실패했습니다.");
			}
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", e.getMessage());
		}
		return result;
	}

	@PostMapping("/api/group/{groupNo}/delete")
	@ResponseBody
	public Map<String, Object> deleteGroup(@PathVariable("groupNo") int groupNo, HttpSession session) {
		System.out.println("그룹 삭제 호출");
		String memberId = (String) session.getAttribute("loginId");
		Map<String, Object> result = new HashMap<>();

		if (memberId == null) {
			result.put("success", false);
			result.put("message", "로그인이 필요합니다.");
			return result;
		}

		try {
			boolean success = service.deleteGroup(groupNo, memberId);
			if (success) {
				result.put("success", true);
				result.put("message", "그룹이 삭제되었습니다.");
			} else {
				result.put("success", false);
				result.put("message", "그룹 삭제에 실패했습니다.");
			}
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", e.getMessage());
		}
		return result;
	}

	@PostMapping("/api/group/{groupNo}/leave")
	@ResponseBody
	public Map<String, Object> leaveGroup(@PathVariable("groupNo") int groupNo, HttpSession session) {

		String memberId = (String) session.getAttribute("loginId");
		Map<String, Object> result = new HashMap<>();

		if (memberId == null) {
			result.put("success", false);
			result.put("message", "로그인이 필요합니다.");
			return result;
		}

		try {
			boolean success = service.leaveGroup(groupNo, memberId);
			if (success) {
				result.put("success", true);
				result.put("message", "그룹에서 탈퇴했습니다.");
			} else {
				result.put("success", false);
				result.put("message", "그룹 탈퇴에 실패했습니다.");
			}
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", e.getMessage());
		}
		return result;
	}

	@GetMapping("/group/form")
	public String inputFormPage(HttpSession session, Model model) {
		String loginId = (String) session.getAttribute("loginId");
		model.addAttribute("loginId", loginId);
		return "group/form-group";
	}

	@PostMapping("/group/add")
	public String addGroup(@RequestParam("groupName") String groupName, HttpSession session) {
		String creatorId = (String) session.getAttribute("loginId");
		service.insertGroupAndMemeber(groupName, creatorId);

		return "main/main";
	}

	@PostMapping("/group/create-with-invites")
	@ResponseBody
	public Map<String, Object> createGroupWithInvites(@RequestParam("groupName") String groupName,
			@RequestParam("memberIds") List<String> memberIds, HttpSession session) {
		System.out.println("=== 컨트롤러 진입 ===");
		System.out.println("groupName: " + groupName);
		System.out.println("memberIds: " + memberIds);
		System.out.println("memberIds size: " + (memberIds != null ? memberIds.size() : "null"));

		String creatorId = (String) session.getAttribute("loginId");
		Map<String, Object> result = new HashMap<>();

		try {
			// 1. 그룹 생성
			System.out.println("=== 그룹 생성 시작 ==="); // 디버깅
			GroupDto dto = service.createGroup(groupName, creatorId);

			if (dto == null) {
				result.put("success", false);
				result.put("message", "그룹 생성에 실패했습니다. 그룹명이 이미 존재하거나 오류가 발생했습니다.");
				return result;
			}

			int groupNo = dto.getGroupNo();
			System.out.println("그룹 생성 성공: groupNo = " + groupNo);

			// 2. 멤버들에게 초대 보내기
			for (String memberId : memberIds) {
				inviteService.inviteToGroup(groupNo, creatorId, memberId);
			}

			result.put("success", true);
			result.put("groupNo", groupNo);
			result.put("message", "그룹이 생성되고 " + memberIds.size() + "명에게 초대를 보냈습니다.");
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "그룹 생성 중 오류가 발생했습니다.");
		}

		return result;
	}

	@GetMapping("/group/list")
	public String groupList(Model model) {

		System.out.println("그룹 리스트 요청이 들어왔어요");// 요청은 찍히고
		List<GroupDto> list = service.selectGroupAll();
		model.addAttribute("list", list);

		for (GroupDto vo : list) {
			System.out.println(vo);
		}

		return "group/group-list-fragment";
	}

	@GetMapping("/group/list/ajax")
	public String groupListAjax(Model model) {
		List<GroupDto> list = service.selectGroupAll();
		model.addAttribute("list", list);
		return "group/group-list-fragment"; // HTML 조각만 반환
	}

	@GetMapping("/group/{groupNo}")
	public String groupHome(@PathVariable("groupNo") int groupNo, Model model, HttpSession session) {
		String loginId = (String) session.getAttribute("loginId");

		if (loginId == null || !service.isGroupMember(groupNo, loginId)) {
			// 멤버가 아니거나 로그인을 안 했으면 메인 페이지로 리다이렉트
			return "redirect:/";
		}

		GroupDto groupInfo = service.getGroupInfo(groupNo);

		// List<BoardVO> groupPosts = boardService.getBoardsByGroupNo(groupNo);
		List<BoardVO> groupReviewPosts = boardService.findByGroupNoAndBoardTypeOrderByBoardNoDesc(groupNo, "REVIEW");
		model.addAttribute("groupInfo", groupInfo);
		model.addAttribute("groupPosts", groupReviewPosts);
		return "group/group-home";
	}

}
