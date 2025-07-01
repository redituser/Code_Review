package com.assignment.liveroom.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.assignment.liveroom.vo.LiveRoomVO;

public interface LiveRoomRepository extends JpaRepository<LiveRoomVO, Long> {

	List<LiveRoomVO> findByCreatorId(String creatorId);

	@Query("SELECT r FROM LiveRoomVO r JOIN LiveRoomMemberVO m ON r.roomId = m.id.roomId WHERE m.id.memberId = :memberId ORDER BY r.createDate DESC")
	List<LiveRoomVO> findRoomsByMemberId(@Param("memberId") String memberId);

}
