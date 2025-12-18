package com.board.service;

import com.board.domain.MemberDTO;
import com.board.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override // CREATE 회원등록(회원가입)
    public boolean registerMember(MemberDTO params) {
        // 아이디가 이미 존재하면 가입 불가
        if (memberMapper.selectIdOverlapCheck(params.getMemberId()) > 0) {
            return false;
        }

        // 비밀번호 BCrypt 암호화
        String encodedPassword = passwordEncoder.encode(params.getPassword()); // 입력받은 비밀번호를 BCrypt형태로 암호화.
        params.setPassword(encodedPassword); // 매개변수에 암호화된 비밀번호를 담음.

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

        if (memberDTO == null || // 1. id 확인
                !"ACTIVE".equals(memberDTO.getStatus()) ||  // 2. 계정상태 확인.
                !passwordEncoder.matches(params.getPassword(), memberDTO.getPassword())) { //  3. BCrypt 비밀번호 확인
            // 1. 평문 비밀번호를 DB에 저장하면, BCrypt에 의해 랜덤 salt가 포함된 수많은 해시값 중 '하나'가 생성되어 저장된다.
            // 2. 평문 비밀번호를 입력하면, 계정 비밀번호로 DB에 저장된 한 개의 해시값과 비교검증 시작.
            // 3. 입력받은 비밀번호가 저장된 해시값을 만들 수 있는 비밀번호인지 검증.
            // 4. 가능하면 로그인 성공, 불가능하면 로그인 실패.

            return null;
        }

        memberMapper.updateLastLogin(memberDTO); // 마지막 로그인 시간 갱신.
        return memberDTO;
    }
}
