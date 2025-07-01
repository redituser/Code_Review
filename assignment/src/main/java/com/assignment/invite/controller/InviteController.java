package com.assignment.invite.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.assignment.invite.service.InviteService;
import com.assignment.invite.vo.InviteDTO;

import jakarta.servlet.http.HttpSession;

@Controller
public class InviteController {

	@Autowired
	InviteService service;

	@PostMapping("/group/invites")
	public String sendInvite(@RequestParam("groupNo") int groupNo, @RequestParam("inviteeId") String inviteeId,
			HttpSession session) {
		String inviterId = (String) session.getAttribute("loginId");

		if (inviterId == null) {
			return "redirect:/user/login";
		}

		if (service.inviteToGroup(groupNo, inviterId, inviteeId)) {
			return "redirect:/group/detail/" + groupNo + "?invite=success";
		} else {
			return "redirect:/group/detail/" + groupNo + "?invite=fail";
		}
	}

	// 초대 목록 조회
	@GetMapping("/invite/my")
	public String myInvitesList(HttpSession session, Model model) {

		String loginId = (String) session.getAttribute("loginId");

		List<InviteDTO> list = service.getMyUnifiedInvites(loginId);

		model.addAttribute("invites", list);

		return "invite/my-invites";

	}

	@PostMapping("/invite/accept")
	public String acceptInvite(@RequestParam("inviteNo") int inviteNo, HttpSession session) {
		String loginId = (String) session.getAttribute("loginId");

		boolean success = service.acceptInvite(inviteNo, loginId);

		return "redirect:/invite/my?result=" + (success ? "accept" : "faill");

	}

	@PostMapping("/invite/reject")
	public String rejectInvite(@RequestParam("inviteNo") int inviteNo) {
		service.rejectInvite(inviteNo);
		return "redirect:/invite/my?result=reject";
	}

	@GetMapping("/invite/my/ajax-data")
	@ResponseBody // 이 어노테이션이 핵심! 리턴값을 순수 데이터(JSON)로 만들어줍니다.
	public ResponseEntity<List<InviteDTO>> myInvitesAjaxData(HttpSession session) {
	    String loginId = (String) session.getAttribute("loginId");
	    if (loginId == null) {
	        // 로그인하지 않은 사용자는 빈 목록과 함께 401 Unauthorized 응답
	        return ResponseEntity.status(401).body(Collections.emptyList());
	    }
	    // 이전에 만든 통합 초대 목록 조회 서비스 호출
	    List<InviteDTO> invites = service.getMyUnifiedInvites(loginId);
	    return ResponseEntity.ok(invites); // 조회된 데이터를 JSON으로 반환
	}

	@PostMapping("/api/group/{groupNo}/invite")
	@ResponseBody
	public Map<String, Object> inviteMembersToGroup(@PathVariable("groupNo") int groupNo,
			@RequestParam("memberIds") List<String> memberIds, HttpSession session) {

		String inviterId = (String) session.getAttribute("loginId");
		Map<String, Object> result = new HashMap<>();

		try {
			// 이미 구현된 inviteService를 재활용
			for (String memberId : memberIds) {
				// 여기에 이미 그룹 멤버인지 확인하는 로직을 추가하면 더 좋..
				service.inviteToGroup(groupNo, inviterId, memberId);
			}
			result.put("success", true);
			result.put("message", memberIds.size() + "명에게 초대를 보냈습니다.");
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "초대 중 오류가 발생했습니다.");
		}

		return result;
	}

}
