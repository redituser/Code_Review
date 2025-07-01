package com.assignment.liveroom.vo;

public class LiveRoomParticipantDto {
	private String memberId;
	private String nickname;

	// JPQL의 'new' 키워드가 사용하려면, 매개변수가 있는 생성자가 필요합니다.
	public LiveRoomParticipantDto(String memberId, String nickname) {
		this.memberId = memberId;
		this.nickname = nickname;
	}

	// Getters
	public String getMemberId() {
		return memberId;
	}

	public String getNickname() {
		return nickname;
	}

}
