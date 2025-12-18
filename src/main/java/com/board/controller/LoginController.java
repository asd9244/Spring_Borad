package com.board.controller;

import com.board.domain.MemberDTO;
import com.board.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class LoginController {

    @Autowired
    private MemberService memberService;

    // 로그인 화면 요청
    @GetMapping("/login.do")
    public String openLoginPage() {
        return "login"; // login page html 주소.
    }

    // 로그인 처리
    @PostMapping("/login.do")
    public String doLogin(@ModelAttribute MemberDTO params, HttpServletRequest request, HttpSession session, Model model) {
        // 로그인 검증
        MemberDTO loginMember = memberService.memberLoginId(params);
        // 로그인 실패. 화면 유지, 메시지 전달.
        if(loginMember == null) { // 에러메시지는 한 번만 띄우고 사라져야 하기 때문에, model객체에 할당함.
            model.addAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.");
            return "login";
        }
        // 로그인 성공, 기존 세션 파기, 새 세션 발급, 새 세션에 로그인 정보 저장.
        session.invalidate(); // 기존 세션 파기.
        HttpSession newSession = request.getSession(true); // 새 세션 발급.
        newSession.setAttribute("LOGIN_MEMBER", loginMember); // 새 세션에 로그인 정보 저장.

        // 관리자로 로그인 할 경우.
        if ("ADMIN".equals(loginMember.getRole())) {
            // 관리자 계정이면 관리자 대시보드로
            return "redirect:/admin/dashboard.do";
        }

        // redirect요청 시, model객체는 메시지만 전달하고 사라진다.
        // 서버메모리에 저장된 session객체는 로그인 정보를 담은 상태로 남는다.
        return "redirect:/board/list.do"; // 로그인 성공, 게시판으로 이동.
    }


    // 로그아웃 처리
    @PostMapping("/logout.do")
    public String logout(HttpSession session) {
        // 세션이 남아있으면, 무효화
        if(session != null) {
            session.invalidate();
        }
        return "redirect:/login.do"; // 로그아웃 후, 로그인페이지로 이동.
    }
}
