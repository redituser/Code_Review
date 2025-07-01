package com.assignment.invite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.group.dao.GroupMapper;
import com.assignment.invite.dao.InviteMapper;
import com.assignment.invite.vo.InviteDTO;
import com.assignment.liveroom.service.LiveRoomService;
import com.assignment.member.dao.MemberRepository;

import jakarta.transaction.Transactional;


@Service
public class InviteService {

	@Autowired
	InviteMapper inviteMapper;

	@Autowired
	GroupMapper groupMapper;

	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	LiveRoomService liveRoomService;

	// 초대 보내기
	public boolean inviteToGroup(int groupNo, String inviterId, String inviteeId) {

		// 초대를 받을 사용자가 존재 하는지
		if (!memberRepository.existsById(inviteeId)) {
			return false;
		}
		// 일단 본인 그룹에 이미 있는 사람인지
		if (groupMapper.selectCountByGroupMember(groupNo,inviteeId) > 0) {
			return false;
		}
		// 이미 초대를 보냈는지
		if (inviteMapper.countPendingInvite(groupNo, inviteeId) > 0) {
			return false; // 이미 초대함
		}

		return inviteMapper.insertInvite(groupNo, inviterId, inviteeId) > 0;

	}

	//초대 목록 조회
	public List<InviteDTO> getMyUnifiedInvites(String loginId) {
	    return inviteMapper.getUnifiedInvitesByMemberId(loginId);
	}

	@Transactional
	public boolean acceptInvite(int inviteNo, String inviteeId) {
		// 초대 정보 조회
		InviteDTO invite = inviteMapper.selectInviteById(inviteNo);

		if (invite == null) {
			return false;
		}

		int memberResult = groupMapper.insertGroupMember(invite.getGroupNo(), inviteeId, "M");

		int inviteResult = inviteMapper.updateInviteStatus(inviteNo, "A");

		return memberResult > 0 && inviteResult > 0;

	}
	
	//초대 거절
	public boolean rejectInvite(int inviteNo) {
		return inviteMapper.updateInviteStatus(inviteNo, "R") > 0;
	}
	
	@Transactional
	public boolean acceptUnifiedInvite(int inviteNo, String inviteeId, String inviteType) {
	    if ("GROUP".equals(inviteType)) {
	        // 기존 그룹 초대 수락 로직 호출
	        return acceptInvite(inviteNo, inviteeId);
	    } else if ("LIVEROOM".equals(inviteType)) {
	        // LiveRoomService의 초대 수락 로직을 호출
	        // (LiveRoomService를 @Autowired로 주입받아야 함)
	        liveRoomService.acceptInvite(inviteNo, inviteeId);
	        return true; // 성공 여부를 liveRoomService에서 예외로 처리한다면
	    }
	    return false;
	}
	
	
	
	
	

}
