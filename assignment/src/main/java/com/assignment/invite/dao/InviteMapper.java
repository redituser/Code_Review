package com.assignment.invite.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.assignment.invite.vo.InviteDTO;

@Mapper
public interface InviteMapper {

	@Insert("INSERT INTO CC_GROUP_INVITE (INVITE_NO, GROUP_NO, INVITER_ID, INVITEE_ID, STATUS, CREATE_DATE)"
			+ " VALUES(SEQ_CC_INVITE.NEXTVAL, #{groupNo} , #{inviterId} , #{inviteeId} , 'W' , SYSDATE)")
	int insertInvite(@Param("groupNo") int groupNo, @Param("inviterId") String inviterId, @Param("inviteeId") String inviteeId);

	// 2. 내가 받은 초대 목록 조회
	@Select("SELECT * FROM CC_GROUP_INVITE WHERE INVITEE_ID = #{inviteeId} AND STATUS = 'W'")
	List<InviteDTO> selectMyInviteList(@Param("inviteeId") String inviteeId);

	// 3. 초대 응답 (수락/거절)
	@Update("UPDATE CC_GROUP_INVITE SET STATUS = #{status} WHERE INVITE_NO = #{inviteNo}")
	int updateInviteStatus(@Param("inviteNo") int inviteNo, @Param("status") String status);

	// 4. 중복 초대 체크
	@Select("SELECT COUNT(*) FROM CC_GROUP_INVITE WHERE GROUP_NO = #{groupNo} AND INVITEE_ID = #{inviteeId} AND STATUS = 'W'")
	int countPendingInvite(@Param("groupNo") int groupNo, @Param("inviteeId") String inviteeId);

	// 5. 특정 초대 정보 조회
	@Select("SELECT * FROM CC_GROUP_INVITE WHERE INVITE_NO = #{inviteNo}")
	InviteDTO selectInviteById(@Param("inviteNo") int inviteNo);

	// JOIN을 사용해서 초대 정보 + 그룹명 함께 조회
	@Select("SELECT i.INVITE_NO, i.GROUP_NO, i.INVITER_ID, i.INVITEE_ID, "
			+ "       i.STATUS, i.CREATE_DATE, g.GROUP_NAME " + "FROM CC_GROUP_INVITE i "
			+ "JOIN CC_GROUP g ON i.GROUP_NO = g.GROUP_NO " + "WHERE i.INVITEE_ID = #{inviteeId} AND i.STATUS = 'W'")
	List<InviteDTO> selectMyInvitesWithGroupName(@Param("inviteeId") String inviteeId);
	
	@Select("""
			SELECT
		        i.INVITE_NO         AS inviteNo,
		        i.INVITER_ID        AS inviterId,
		        g.GROUP_NAME        AS targetName,
		        g.GROUP_NO          AS targetId,
		        'GROUP'             AS inviteType
		    FROM
		        CC_GROUP_INVITE i
		    JOIN
		        CC_GROUP g ON i.GROUP_NO = g.GROUP_NO
		    WHERE
		        i.INVITEE_ID = #{memberId} AND i.STATUS = 'W'
		
		    UNION ALL
		
		    SELECT
		        i.INVITE_NO         AS inviteNo,
		        i.INVITER_ID        AS inviterId,
		        r.ROOM_NAME         AS targetName,
		        r.ROOM_ID           AS targetId,
		        'LIVEROOM'          AS inviteType
		    FROM
		        CC_LIVE_ROOM_INVITE i
		    JOIN
		        CC_LIVE_ROOM r ON i.ROOM_ID = r.ROOM_ID
		    WHERE
		        i.INVITEE_ID = #{memberId} AND i.STATUS = 'W'
		""")
		List<InviteDTO> getUnifiedInvitesByMemberId(@Param("memberId") String memberId);

	
	

}
