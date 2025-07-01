package com.assignment.group.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.group.dao.GroupMapper;
import com.assignment.group.dao.GroupRepository;
import com.assignment.group.vo.GroupDto;
import com.assignment.group.vo.GroupMemberDto;
import com.assignment.group.vo.GroupVO;

import jakarta.transaction.Transactional;

@Service
public class GroupService {

	@Autowired
	GroupRepository repository;

	@Autowired
	GroupMapper mapper;

//	public List<GroupVO> selectGroupAll() {
//		List<GroupVO> list = mapper.selectAll();
//		return list;
//	}

	public List<GroupDto> selectGroupAll() {
		System.out.println("Service: selectGroupAll 호출됨");
		List<GroupDto> result = mapper.selectAll(); // 또는 repository 사용
		System.out.println("Service: 조회된 데이터 개수 = " + (result != null ? result.size() : "null"));
		return result;
	}

	@Transactional
	public boolean insertGroupAndMemeber(String groupName, String creatorId) {
		if (mapper.countByGroupName(groupName) > 0) {
			throw new RuntimeException("이미 존재하는 그룹명입니다.");
		}

		if (mapper.insertGroup(groupName, creatorId) > 0) {
			int newGroupNo = mapper.getLastGroupNo();
			int memberResult = mapper.insertGroupMember(newGroupNo, creatorId, "L");
			return memberResult > 0;
		}
		return false;
	}
	
	 @Transactional
	    public boolean delegateLeader(int groupNo, String currentLeaderId, String newLeaderId) {
	        // 1. 현재 사용자가 정말로 리더인지 확인 (보안)
	        String currentUserType = mapper.findMemberType(groupNo, currentLeaderId);
	        if (!"L".equals(currentUserType)) {
	            throw new IllegalStateException("리더 위임은 현재 그룹의 리더만 가능합니다.");
	        }

	        // 2. 기존 리더를 멤버로 강등
	        int demoteResult = mapper.updateMemberType(groupNo, currentLeaderId, "M");

	        // 3. 새로운 멤버를 리더로 승급
	        int promoteResult = mapper.updateMemberType(groupNo, newLeaderId, "L");

	        // 4. CC_GROUP 테이블의 CREATOR_ID(대표자)도 새로운 리더로 변경
	        int updateCreatorResult = mapper.updateGroupCreator(groupNo, newLeaderId);

	        // 모든 업데이트가 성공했는지 확인
	        return demoteResult > 0 && promoteResult > 0 && updateCreatorResult > 0;
	    }
	 
	  @Transactional
	    public boolean deleteGroup(int groupNo, String memberId) {
	        // 1. 현재 사용자가 리더인지 확인 (보안)
	        String memberType = mapper.findMemberType(groupNo, memberId);
	        if (!"L".equals(memberType)) {
	            throw new IllegalStateException("그룹 삭제는 리더만 가능합니다.");
	        }

	        // 2. 그룹의 모든 멤버를 먼저 삭제 처리
	        mapper.deleteAllMembersByGroupNo(groupNo);

	        // 3. 그룹 자체를 삭제 처리
	        return mapper.selectDeleteGroup(groupNo) > 0;
	    }
	 
	  
	  public boolean leaveGroup(int groupNo, String memberId) {
	        // 1. 리더는 탈퇴할 수 없도록 처리
	        String memberType = mapper.findMemberType(groupNo, memberId);
	        if ("L".equals(memberType)) {
	            throw new IllegalStateException("리더는 그룹을 탈퇴할 수 없습니다. 리더를 위임하거나 그룹을 삭제해주세요.");
	        }

	        // 2. 그룹에서 멤버 삭제 처리
	        return mapper.selectDeleteGroupMember(memberId, groupNo) > 0;
	    }

	public GroupDto createGroup(String groupName, String creatorId) {
		// 조건
		// 그룹을 추가했을때
		// 그 그룹이 추가되었다면
		// 그룹 번호 반환
		if (insertGroupAndMemeber(groupName, creatorId)) {
			GroupDto dto = mapper.selectCreatorIdAndGroupName(groupName, creatorId); // GroupMapper의 매개변수 순서가 다르면 에러가남
			return dto;
		}
		return null;

	}

	public List<GroupDto> getMyGroups(String memberId) {
		return mapper.selectMyGroups(memberId);
	}

	
	//특정 그룹의 멤버원들을 반환
	public List<GroupMemberDto> selectMyGroupList(int groupNo) {
		return mapper.findMembersWithNicknameByGroupNo(groupNo);
		
	}

	public boolean deleteGroup(int groupNo) {
		return mapper.selectDeleteGroup(groupNo) > 0;
	}

	public boolean deleteMember(String memberId, int groupNo) {
		return mapper.selectDeleteGroupMember(memberId, groupNo) > 0;
	}

	public boolean addMemberToGroup(int GroupNo, String memberId) {
		return mapper.insertGroupMember(GroupNo, memberId, "M") > 0;
	}
	
	public GroupDto getGroupInfo(int gorupNo) {
		return mapper.getGroupInfo(gorupNo);
	}
	
	public boolean isGroupMember(int groupNo, String memberId) {
	    return mapper.isGroupMember(groupNo, memberId) > 0;
	}


}
