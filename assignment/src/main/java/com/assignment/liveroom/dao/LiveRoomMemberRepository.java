package com.assignment.liveroom.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.assignment.liveroom.vo.LiveRoomMemberId;
import com.assignment.liveroom.vo.LiveRoomMemberVO;
import com.assignment.liveroom.vo.LiveRoomParticipantDto;
import com.assignment.liveroom.vo.LiveRoomVO;

public interface LiveRoomMemberRepository extends JpaRepository<LiveRoomMemberVO, LiveRoomMemberId> {

	List<LiveRoomMemberVO> findById_MemberId(String MemberId);

	List<LiveRoomMemberVO> findById_RoomId(Long roomId);

	@Query("SELECT r FROM LiveRoomVO r JOIN LiveRoomMemberVO m ON r.roomId = m.id.roomId WHERE m.id.memberId = :memberId ORDER BY r.createDate DESC")
	List<LiveRoomVO> findRoomsByMemberId(@Param("memberId") String memberId);

	@Query("SELECT new com.assignment.liveroom.vo.LiveRoomParticipantDto(m.id.memberId, mem.nickName) "
			+ "FROM LiveRoomMemberVO m, com.assignment.member.vo.MemberVO mem "
			+ "WHERE m.id.memberId = mem.id AND m.id.roomId = :roomId")
	List<LiveRoomParticipantDto> findParticipantsByRoomId(@Param("roomId") Long roomId);

	void deleteAllById_RoomId(Long roomId);


	@Modifying
	@Query(value = "INSERT INTO CC_LIVE_ROOM_MEMBER (ROOM_ID, MEMBER_ID, JOIN_DATE) VALUES (:roomId, :memberId, SYSDATE)", nativeQuery = true)
	void addParticipant(@Param("roomId") Long roomId, @Param("memberId") String memberId);

}
