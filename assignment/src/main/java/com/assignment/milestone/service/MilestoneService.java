package com.assignment.milestone.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.group.dao.GroupMapper;
import com.assignment.milestone.dao.MilestoneRepository;
import com.assignment.milestone.vo.MilestoneVO;

@Service
public class MilestoneService {

    @Autowired
    private MilestoneRepository milestoneRepository;

    @Autowired
    private GroupMapper groupMapper;
    
    
    public List<MilestoneVO> getMilestonesByGroup(Integer groupNo) {
        return milestoneRepository.findByGroupNoOrderByDueDateAsc(groupNo);
    }
    
    
    public MilestoneVO createMilestone(MilestoneVO milestone , String memberId) {
    	  int memberCount = groupMapper.isGroupMember(milestone.getGroupNo(), memberId);
          if (memberCount == 0) {
              // 멤버가 아니면 예외를 발생시키거나 null을 반환
              throw new SecurityException("해당 그룹의 멤버만 마일스톤을 생성할 수 있습니다.");
          }
          return milestoneRepository.save(milestone);
    }
    
    
    public MilestoneVO updateMilestone(Integer milestoneId, MilestoneVO milestoneDetails) {
        MilestoneVO existingMilestone = milestoneRepository.findById(milestoneId) //orElse 는 처음보네
                .orElseThrow(() -> new IllegalArgumentException("해당 마일스톤을 찾을 수 없습니다. ID: " + milestoneId));

        // 제목, 설명, 마감일, 상태 등 필요한 필드를 업데이트합니다.
        existingMilestone.setTitle(milestoneDetails.getTitle());
        existingMilestone.setDescription(milestoneDetails.getDescription());
        existingMilestone.setDueDate(milestoneDetails.getDueDate());
        existingMilestone.setStatus(milestoneDetails.getStatus());

        return milestoneRepository.save(existingMilestone);
    }
    
    
    public void deleteMilestone(Integer milestoneId) {
        milestoneRepository.deleteById(milestoneId);
    }
    
    

    
    
    
    
    
    
    
	
}
