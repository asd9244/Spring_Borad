package com.board.mapper;

import com.board.domain.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {

    // Create, Update, Delete 쿼리의 마이바티스 Return Type: int

    // CREATE 회원등록, 작업 성공 시 생성된 행의 수(1)를 반환한다.
    public int insertMember(MemberDTO params);

    // UPDATE 회원정보, 작업 성공 시 변경된 행의 수(1)를 반환한다.
    public int updateMember(MemberDTO params);

    //SELECT 회원조회, 회원 1명의 상세정보를 조회한다.
    public MemberDTO selectMemberDetail(String memberId);

    // SELECT 전체회원조회, 회원 전체의 상세정보를 조회한다.
    public List<MemberDTO> selectMemberList(MemberDTO params);

    // UPDATE 회원탈퇴, 회원의 status를 inactive로 변경한다. 작업 성공 시 행의 수(1)을 반환한다.
    public int deleteMember(String memberId);

    // SELECT 중복회원확인, 0보다 큰 수가 반환되면, 아이디 중복.
    public int selectMemberIdCheck(String memberId);

    // SELECT 중복이메일확인, 0보다 큰 수가 반환되면, 아이디 중복.
    public int selectEmailCheck(String email);

    // SELECT 계정상태조회, 로그인 할 때, status가 inactive면 로그인 중단.
    public String selectMemberStatus(String memberId);

    // SELECT 로그인기능, 아이디로 비밀번호를 조회해서, 일치하는지 확인. 일치하면 1 반환.
    public MemberDTO selectMemberById(String memberId);

    // UPDATE 마지막접속시간, 로그인 할 때 접속시간 Update
    public int updateLastLogin(MemberDTO params);

}
