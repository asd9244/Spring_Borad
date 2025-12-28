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

            // 1. 세션에서 "targetUrl" 데이터 조회
            // 세션 객체(Map 구조)에 저장된 값이 Object 타입이므로, String으로 형변환(Casting)하여 가져옵니다.
            String targetUrl = (String) newSession.getAttribute("targetUrl");

            // 2. 데이터 존재 여부 확인
            if (targetUrl != null) { 
                // 3. 데이터 삭제 (일회성 사용)
                // 사용한 URL 정보는 세션 메모리에서 즉시 제거하여, 이후 불필요한 리다이렉트를 방지합니다.
                newSession.removeAttribute("targetUrl"); 
                
                // 4. 저장된 URL로 리다이렉트
                // "redirect:" 접두사를 반환하면, Spring의 ViewResolver가 이를 인식하여 해당 주소로 리다이렉트 응답을 보냅니다.
                return "redirect:" + targetUrl; 
            }

        // 5. 저장된 데이터가 없으면 기본 페이지로 이동
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
