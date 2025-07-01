package com.assignment.group.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.assignment.group.vo.GroupDto;
import com.assignment.group.vo.GroupMemberDto;


@Mapper
public interface GroupMapper {

	//그룹을 만들면 자동으로 Type 이 L이 되게 해야함 
	@Insert("INSERT INTO CC_GROUP(GROUP_NO , GROUP_NAME , CREATOR_ID , CREATE_DATE ) "
			+ "VALUES (SEQ_CC_GROUP.NEXTVAL , #{groupName} , #{creatorId} , SYSDATE )")
	int insertGroup(@Param("groupName") String groupName , @Param("creatorId") String creatorId);
	

	//특정 그룹원 추가
	@Insert("INSERT INTO CC_GROUP_MEMBER(GROUP_NO , MEMBER_ID , Type,CREATE_DATE , DELETE_DATE )"
			+ "VALUES (#{groupNo} , #{memberId} , #{type} ,SYSDATE, NULL ) ")
	int insertGroupMember(@Param("groupNo") int groupNo,@Param("memberId") String memberId , @Param("type") String Type );
	
	// 마지막에 생성된 그룹 번호 조회 (중요!)
    @Select("SELECT SEQ_CC_GROUP.CURRVAL FROM DUAL")
    int getLastGroupNo();
	
	
	//모든 그룹 조회
	@Select("SELECT * FROM CC_GROUP where DELETE_DATE IS NULL")
	List<GroupDto> selectAll();
	
	//특정 그룹 조회
	@Select("SELECT * FROM CC_GROUP WHERE GROUP_NO = #{groupNo} AND DELETE_DATE IS NULL")
	List<GroupDto> selectMyGroupList(@Param("groupNo") int groupNo);
	
	@Select("SELECT * FROM CC_GROUP WHERE GROUP_NO = #{groupNo} AND DELETE_DATE IS NULL")
	GroupDto getGroupInfo(@Param("groupNo") int groupNo);
	
	
	//특정 그룹 조회(그룹장 아이디 , 그룹 이름)
	@Select("SELECT * FROM CC_GROUP WHERE GROUP_NAME = #{groupName}  AND CREATOR_ID = #{creatorId} AND DELETE_DATE IS NULL")
	GroupDto selectCreatorIdAndGroupName( @Param("groupName") String groupName , @Param("creatorId") String creatorId);
	
	//특정 그룹원 조회
	@Select("SELECT COUNT(*)FROM CC_GROUP_MEMBER WHERE GROUP_NO = #{groupNo}  AND MEMBER_ID = #{memberId} AND DELETE_DATE IS NULL")
	int selectCountByGroupMember(@Param("groupNo") int groupNo , @Param("memberId") String memberId);
	
	
	//특정 그룹 삭제 (삭제 날짜만 표시)
	@Update("UPDATE CC_GROUP SET DELETE_DATE = SYSDATE WHERE GROUP_NO = #{groupNo}")
	int selectDeleteGroup(@Param("groupNo") int groupNo);
	
	//특정 그룹원 삭제
	@Update("UPDATE CC_GROUP_MEMBER SET DELETE_DATE = SYSDATE WHERE MEMBER_ID = #{memberId} AND GROUP_NO = #{groupNo}")
	int selectDeleteGroupMember(@Param("memberId") String memberId , @Param("groupNo") int groupNo);

	@Select("SELECT COUNT(*) FROM CC_GROUP WHERE GROUP_NAME = #{groupName} AND DELETE_DATE IS NULL")
    int countByGroupName(@Param("groupName") String groupName);
	
	
	//그룹 보기 (본인 그룹)
	@Select("SELECT g.GROUP_NO, g.GROUP_NAME, g.CREATOR_ID, g.CREATE_DATE, " +
			"       gm.TYPE as memberType " +
			"FROM CC_GROUP g " +
			"JOIN CC_GROUP_MEMBER gm ON g.GROUP_NO = gm.GROUP_NO " +
			"WHERE gm.MEMBER_ID = #{memberId} " +
			"AND g.DELETE_DATE IS NULL AND gm.DELETE_DATE IS NULL " +
			"ORDER BY g.CREATE_DATE DESC")
	List<GroupDto> selectMyGroups(@Param("memberId") String memberId);
	
	
	@Select("SELECT " +
            "    gm.MEMBER_ID as memberId, " +
            "    u.NICKNAME as nickname, " +          // 닉네임 컬럼을 조회합니다.
            "    gm.TYPE as type " +
            "FROM " +
            "    CC_GROUP_MEMBER gm " +
            "JOIN " +
            "    CC_MEMBER u ON gm.MEMBER_ID = u.ID " + // 사용자 테이블과 JOIN 합니다.
            "WHERE " +
            "    gm.GROUP_NO = #{groupNo} AND gm.DELETE_DATE IS NULL")
	 List<GroupMemberDto> findMembersWithNicknameByGroupNo(@Param("groupNo") int groupNo);

	  // 특정 그룹 멤버의 역할을 변경하는 쿼리 (리더 L, 멤버 M)
    @Update("UPDATE CC_GROUP_MEMBER SET TYPE = #{type} WHERE GROUP_NO = #{groupNo} AND MEMBER_ID = #{memberId}")
    int updateMemberType(@Param("groupNo") int groupNo, @Param("memberId") String memberId, @Param("type") String type);

    // 그룹 정보의 생성자(리더)를 변경하는 쿼리
    @Update("UPDATE CC_GROUP SET CREATOR_ID = #{newLeaderId} WHERE GROUP_NO = #{groupNo}")
    int updateGroupCreator(@Param("groupNo") int groupNo, @Param("newLeaderId") String newLeaderId);

    // 특정 멤버의 역할(TYPE)을 조회하는 쿼리 (권한 확인용)
    @Select("SELECT TYPE FROM CC_GROUP_MEMBER WHERE GROUP_NO = #{groupNo} AND MEMBER_ID = #{memberId} AND DELETE_DATE IS NULL")
    String findMemberType(@Param("groupNo") int groupNo, @Param("memberId") String memberId);
    
    // 특정 그룹의 모든 멤버를 삭제 처리 (soft delete)
    @Update("UPDATE CC_GROUP_MEMBER SET DELETE_DATE = SYSDATE WHERE GROUP_NO = #{groupNo}")
    int deleteAllMembersByGroupNo(@Param("groupNo") int groupNo);
    
    @Select("SELECT COUNT(*) FROM CC_GROUP_MEMBER WHERE GROUP_NO = #{groupNo} AND MEMBER_ID = #{memberId} AND DELETE_DATE IS NULL")
    int isGroupMember(@Param("groupNo") int groupNo, @Param("memberId") String memberId);
	
}
