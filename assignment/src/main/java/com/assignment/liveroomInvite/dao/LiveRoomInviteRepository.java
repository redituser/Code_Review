package com.assignment.liveroomInvite.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.assignment.liveroomInvite.vo.LiveRoomInviteVO;

public interface LiveRoomInviteRepository extends JpaRepository<LiveRoomInviteVO, Long> {
	Optional<LiveRoomInviteVO> findByRoomIdAndInviteeIdAndStatus(Long roomId, String inviteeId, String status);

	Optional<LiveRoomInviteVO> findByInviteNoAndInviteeIdAndStatus(int inviteNo, String inviteeId, String status);

	@Modifying
	@Query(value = "UPDATE CC_LIVE_ROOM_INVITE SET STATUS = :status WHERE INVITE_NO = :inviteNo", nativeQuery = true)
	int updateInviteStatus(@Param("inviteNo") int inviteNo, @Param("status") String status);


}
