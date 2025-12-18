package com.board.controller.admin;

import com.board.domain.MemberDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminDashboardController {

    /*
    관리자 대시보드 화면 요청
    - URL : GET /admin/dashboard.do
    - Interceptor에서 ADMIN 권한 보장됨
    */
    @GetMapping("/admin/dashboard.do")
    public String openAdminDashboard(HttpSession session, Model model) {

        // 세션에서 로그인 정보 조회
        MemberDTO loginMember =
                (MemberDTO) session.getAttribute("LOGIN_MEMBER");

        /*
        Interceptor에서 이미 ADMIN만 통과시키므로
        여기서는 role 검사 불필요
        (Controller 단순화)
        */

        // 관리자 정보 화면 전달 (이름, 아이디 표시용)
        model.addAttribute("admin", loginMember);

        // 관리자 대시보드 화면
        return "admin/dashboard"; // templates/admin/dashboard.html
    }
}