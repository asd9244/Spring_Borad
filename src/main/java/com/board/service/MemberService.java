package com.board.service;

import com.board.domain.MemberDTO;

public interface MemberService {

    // CREATE(회원가입)
    public boolean registerMember(MemberDTO params);

    // UPDATE 회원등록, 회원정보수정.
    public boolean updateMember(MemberDTO params);

    // SELECT 회원 상세 조회
    public MemberDTO getMemberDetail(String memberId);

    // UPDATE 회원 삭제
    public boolean deleteMember(String memberId);

    // 아이디중복체크
    public boolean overlapCheckId(String memberId);

    // 이메일중복체크
    public boolean overlapCheckEmail(String email);

    // 로그인 기능
    public MemberDTO memberLoginId(MemberDTO params);
}
