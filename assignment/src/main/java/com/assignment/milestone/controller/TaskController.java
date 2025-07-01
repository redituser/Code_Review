package com.assignment.milestone.controller;

import java.util.List;
import java.util.Map;

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

import com.assignment.milestone.service.TaskService;
import com.assignment.milestone.vo.TaskVO;

@RestController
@RequestMapping("/api/groups/{groupNo}/milestones/{milestoneId}/tasks") 
public class TaskController {
	   @Autowired
	    private TaskService taskService;

	// 마일스톤의 모든 작업 조회
	    @GetMapping
	    // 경로 변수 {groupNo}를 메소드 인자에 추가 (사용하지 않더라도 경로 매핑을 위해 필요)
	    public ResponseEntity<List<TaskVO>> getTasks(@PathVariable("groupNo") Integer groupNo, @PathVariable("milestoneId") Integer milestoneId) {
	        List<TaskVO> tasks = taskService.getTasksByMilestone(milestoneId);
	        return ResponseEntity.ok(tasks);
	    }


	    // 새 작업 생성
	    @PostMapping
	    public ResponseEntity<TaskVO> createTask(@PathVariable("groupNo") Integer groupNo , @PathVariable("milestoneId") Integer milestoneId, @RequestBody TaskVO task) {
	        task.setMilestoneId(milestoneId);
	        TaskVO createdTask = taskService.createTask(task);
	        return ResponseEntity.ok(createdTask);
	    }

	    // 작업 상태 변경
	    @PutMapping("/{taskId}/status")
	    public ResponseEntity<TaskVO> updateTaskStatus(@PathVariable("groupNo") Integer groupNo, @PathVariable("taskId") Integer taskId, @RequestBody Map<String, String> payload) {
	        String status = payload.get("status");
	        TaskVO updatedTask = taskService.updateTaskStatus(taskId, status);
	        return ResponseEntity.ok(updatedTask);
	    }
	    
	    @PutMapping("/{taskId}")
	    public ResponseEntity<TaskVO> updateTask(
	            @PathVariable("groupNo") Integer groupNo,
	            @PathVariable("milestoneId") Integer milestoneId,
	            @PathVariable("taskId") Integer taskId,
	            @RequestBody TaskVO taskDetails) {
	        
	        
	        TaskVO updatedTask = taskService.updateTask(taskId, taskDetails);
	        return ResponseEntity.ok(updatedTask);
	    }
	    

	    // 작업 담당자 할당
	    @PutMapping("/{taskId}/assignee")
	    public ResponseEntity<TaskVO> assignTask(@PathVariable("groupNo") Integer groupNo, @PathVariable("taskId") Integer taskId, @RequestBody Map<String, String> payload) {
	        String assigneeId = payload.get("assigneeId");
	        TaskVO updatedTask = taskService.assignTask(taskId, assigneeId);
	        return ResponseEntity.ok(updatedTask);
	    }
	    
	    // 작업 삭제
	    @DeleteMapping("/{taskId}")
	    public ResponseEntity<Void> deleteTask(
	            @PathVariable("groupNo") Integer groupNo,
	            @PathVariable("milestoneId") Integer milestoneId,
	            @PathVariable("taskId") Integer taskId) {
	        
	        taskService.deleteTask(taskId);
	        return ResponseEntity.noContent().build();
	    }
	
}
