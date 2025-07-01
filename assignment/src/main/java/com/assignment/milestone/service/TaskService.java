package com.assignment.milestone.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // 트랜잭션 어노테이션 추가

import com.assignment.group.dao.GroupMapper; // GroupMapper 임포트
import com.assignment.milestone.dao.MilestoneRepository; // MilestoneRepository 임포트
import com.assignment.milestone.dao.TaskRepository;
import com.assignment.milestone.vo.MilestoneVO; // MilestoneVO 임포트
import com.assignment.milestone.vo.TaskVO;

@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;

    // ▼▼▼ 유효성 검사를 위해 두 개의 Repository/Mapper를 주입합니다. ▼▼▼
	@Autowired
	private MilestoneRepository milestoneRepository;

	@Autowired
	private GroupMapper groupMapper;
    // ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲

	public List<TaskVO> getTasksByMilestone(Integer milestoneId) {
		return taskRepository.findByMilestoneIdOrderByCreateDateAsc(milestoneId);
	}

    @Transactional // 데이터 변경이 있으므로 트랜잭션 처리
	public TaskVO createTask(TaskVO task) {
        // ▼▼▼ 담당자 유효성 검사 로직 추가 ▼▼▼
        validateAssignee(task.getMilestoneId(), task.getAssigneeId());
        // ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲

		return taskRepository.save(task);
	}

	@Transactional
	public TaskVO updateTaskStatus(Integer taskId, String status) {
		TaskVO task = taskRepository.findById(taskId)
				.orElseThrow(() -> new IllegalArgumentException("해당 작업을 찾을 수 없습니다. ID: " + taskId));
		task.setStatus(status);
		return taskRepository.save(task);
	}
	
    @Transactional
	public TaskVO updateTask(Integer taskId, TaskVO taskDetails) {
	    TaskVO existingTask = taskRepository.findById(taskId)
	            .orElseThrow(() -> new IllegalArgumentException("해당 작업을 찾을 수 없습니다. ID: " + taskId));

        // ▼▼▼ 담당자 유효성 검사 로직 추가 ▼▼▼
        validateAssignee(existingTask.getMilestoneId(), taskDetails.getAssigneeId());
        // ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲

	    existingTask.setTitle(taskDetails.getTitle());
	    existingTask.setStartDate(taskDetails.getStartDate());
	    existingTask.setEndDate(taskDetails.getEndDate());
	    existingTask.setAssigneeId(taskDetails.getAssigneeId());
	 
	    return taskRepository.save(existingTask);
	}

    @Transactional
	public TaskVO assignTask(Integer taskId, String assigneeId) {
		TaskVO task = taskRepository.findById(taskId)
				.orElseThrow(() -> new IllegalArgumentException("해당 작업을 찾을 수 없습니다. ID: " + taskId));

        // ▼▼▼ 담당자 유효성 검사 로직 추가 ▼▼▼
        validateAssignee(task.getMilestoneId(), assigneeId);
        // ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲
		
		task.setAssigneeId(assigneeId);
		return taskRepository.save(task);
	}
	
	public void deleteTask(Integer taskId) {
		taskRepository.deleteById(taskId);
	}


    /**
     * 담당자가 유효한 그룹 멤버인지 확인하는 private 헬퍼 메소드
     * @param milestoneId 작업을 포함하는 마일스톤 ID
     * @param assigneeId 검사할 담당자 ID
     */
    private void validateAssignee(Integer milestoneId, String assigneeId) {
        // 담당자가 지정된 경우에만 유효성 검사를 수행합니다. (null 이나 빈 문자열은 담당자 없음으로 간주)
        if (assigneeId == null || assigneeId.trim().isEmpty()) {
            return;
        }

        // 1. 마일스톤 ID로 마일스톤 정보를 조회하여 그룹 번호를 얻습니다.
        MilestoneVO milestone = milestoneRepository.findById(milestoneId)
                .orElseThrow(() -> new IllegalArgumentException("작업을 추가할 마일스톤을 찾을 수 없습니다. ID: " + milestoneId));
        Integer groupNo = milestone.getGroupNo();

        // 2. 그룹 번호와 담당자 ID로 해당 그룹의 멤버가 맞는지 확인합니다.
        int memberCount = groupMapper.isGroupMember(groupNo, assigneeId);
        if (memberCount == 0) {
            // 그룹 멤버가 아니면 예외를 발생시켜 저장을 막습니다.
            throw new IllegalArgumentException("담당자로 지정된 ID '" + assigneeId + "'는 해당 그룹의 멤버가 아닙니다.");
        }
    }
}