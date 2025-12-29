package com.board.controller;

import com.board.domain.MemberDTO;
import com.board.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // 1. 스프링에게 "이 클래스는 웹 요청을 처리하는 컨트롤러입니다"라고 알림
@RequestMapping("/members") // 2. 이 컨트롤러의 모든 주소 앞에 "/members"를 자동으로 붙임
public class MemberController {

    @Autowired // 3. 스프링이 관리하는 빈(Bean) 중 MemberService를 찾아서 주입(DI)해줌
    private MemberService memberService;

    // 메서드 1: 회원가입 화면(HTML)을 보여주는 기능 (GET 방식)
    // 요청 주소: http://localhost:8080/members/add.do
    @GetMapping("/add.do")
    public String openMemberAdd() {
        // 리턴값 "members/add"는
        // src/main/resources/templates/members/add.html 파일을 찾아서 보여주라는 뜻
        return "members/add";
    }

    // 메서드 2: 사용자가 입력한 정보를 받아서 DB에 저장하는 기능 (POST 방식)
    // 요청 주소: http://localhost:8080/members/save.do (HTML 폼에서 action으로 지정)
    @PostMapping("/save.do")
    public String insertMember(MemberDTO params) {
        // 1. 화면(HTML)에서 name 속성이 MemberDTO의 변수명과 일치하면, 자동으로 값이 params에 담김 (커맨드 객체)
        
        // 2. 서비스(로직) 호출하여 회원가입 진행
        boolean isRegistered = memberService.registerMember(params);

        if (isRegistered == false) {
            // 회원가입 실패 시 (예: 중복 아이디 등), 다시 가입 화면으로 돌려보냄
            // TODO: 나중에는 "이미 사용 중인 아이디입니다" 같은 메시지를 띄우는 기능 추가 가능
            return "redirect:/members/add.do";
        }

        // 3. 회원가입 성공 시, 로그인 페이지로 이동(Redirect)
        return "redirect:/login.do";
    }
}