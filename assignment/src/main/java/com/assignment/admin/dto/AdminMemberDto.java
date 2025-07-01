package com.assignment.admin.dto;

import java.time.format.DateTimeFormatter;

import com.assignment.member.vo.MemberVO;
// import java.time.LocalDateTime; // 더 이상 필요 없음

public class AdminMemberDto {

    private String id;
    private String nickName;
    private String email;
    private char type;
    
    // ★★★ [수정 1] 필드 타입을 MemberVO와 동일한 String으로 변경 ★★★
    private String createDate;

    private boolean isAdmin;
    private boolean isUser;

    public AdminMemberDto(MemberVO member) {
        this.id = member.getId();
        this.nickName = member.getNickName();
        this.email = member.getEmail();
        this.type = member.getType();
        
        // ▼▼▼ 날짜 데이터를 원하는 포맷의 문자열로 변경하는 로직 ▼▼▼
        if (member.getCreateDate() != null) {
            // "yyyy-MM-dd HH:mm" 형태로 문자열을 만듭니다.
            this.createDate = member.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } else {
            // 혹시 날짜 데이터가 없는 경우를 대비합니다.
            this.createDate = "날짜 정보 없음";
        }
        
        this.isAdmin = (member.getType() == 'A');
        this.isUser = (member.getType() == 'U');
    }

    // Getters
    public String getId() { return id; }
    public String getNickName() { return nickName; }
    public String getEmail() { return email; }
    public char getType() { return type; }
    
    // ★★★ [수정 3] Getter의 반환 타입도 String으로 변경 ★★★
    public String getCreateDate() { return createDate; }
    
    public boolean isAdmin() { return isAdmin; }
    public boolean isUser() { return isUser; }
}