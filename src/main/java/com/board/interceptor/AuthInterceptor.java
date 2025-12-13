package com.board.interceptor;

import com.board.domain.MemberDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 현재 요청 URI
        String requestURI = request.getRequestURI();

        // 세션 조회 (없으면 null)
        HttpSession session = request.getSession(false);

        // 로그인 정보 조회
        MemberDTO loginMember = (session != null)
                ? (MemberDTO) session.getAttribute("LOGIN_MEMBER")
                : null;

        // 1. 로그인 필요 URL인데 로그인 안 한 경우
        if (loginMember == null) {
            // 로그인 페이지로 이동
            response.sendRedirect("/login.do");
            return false; // 요청 차단
        }

        // 2. 관리자 전용 URL 접근 시 role 검사
        if (requestURI.startsWith("/admin")) {

            // ADMIN 권한이 아니면 접근 차단
            if (!"ADMIN".equals(loginMember.getRole())) {
                // 초기 단계에서는 메인으로 돌려보냄
                response.sendRedirect("/board/list.do");
                return false;
            }
        }

        // 모든 조건 통과 → 요청 진행
        return true;
    }
}
