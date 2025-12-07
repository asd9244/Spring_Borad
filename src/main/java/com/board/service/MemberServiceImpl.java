package com.board.service;

import com.board.domain.MemberDTO;
import com.board.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Override // CREATE 회원등록(회원가입)
    public boolean registerMember(MemberDTO params) {
        // 아이디가 이미 존재하면 가입 불가
        if (memberMapper.selectIdOverlapCheck(params.getMemberId()) > 0) {
            return false;
        }

        // 가입 정상 처리
        int queryResult = memberMapper.insertMember(params);
        return (queryResult == 1);
    }

    @Override // UPDATE 회원등록, 회원정보수정.
    public boolean updateMember(MemberDTO params) {
        return memberMapper.updateMember(params) > 0;
    }


    @Override // SELECT 회원 상세 조회
    public MemberDTO getMemberDetail(String memberId) {
        return memberMapper.selectMemberDetail(memberId);
    }

    @Override // UPDATE 회원 삭제
    public boolean deleteMember(String memberId) {
        int queryResult = 0;
        MemberDTO memberDTO = memberMapper.selectMemberDetail(memberId);

        if (memberDTO != null && "ACTIVE".equals(memberDTO.getStatus())) {
            queryResult = memberMapper.deleteMember(memberId);
        }
        return (queryResult == 1) ? true : false;
    }

    @Override // 아이디중복체크
    public boolean overlapCheckId(String memberId) {
        int queryResult = memberMapper.selectIdOverlapCheck(memberId);
        return (queryResult == 0) ? true : false;
    }

    @Override
    public boolean overlapCheckEmail(String email) {
        int queryResult = memberMapper.selectOverlapEmailCheck(email);
        return (queryResult == 0) ? true : false;
    }

    @Override // 로그인 기능
    public MemberDTO memberLoginId(MemberDTO params) {
        MemberDTO memberDTO = memberMapper.selectMemberById(params.getMemberId());

        if (memberDTO == null || // id 확인
                !"ACTIVE".equals(memberDTO.getStatus()) || // 유저상태 확인
                !memberDTO.getPassword().equals(params.getPassword())) { // 비밀번호 확인
            return null; // 셋 중 하나라도 통과못하면 로그인 실패.
        }
        memberMapper.updateLastLogin(memberDTO);
        return memberDTO;
    }
}
