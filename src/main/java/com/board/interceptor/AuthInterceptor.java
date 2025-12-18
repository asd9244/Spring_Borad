package com.board.interceptor;

import com.board.domain.MemberDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

// Auth: Authentication(인증, 로그인 여부) + Authorization(권한, 관리자 여부)의 줄임말.
// Interceptor 인터페이스를 받아서 Controller보다 먼저 요청을 받는다.
// springframework의 Intercepter이기 때문에 요청이 들어오면, DispatcherServlet이 알아서 interceptor에게 요청을 먼저 넘긴다.
public class AuthInterceptor implements HandlerInterceptor {

    @Override  // pre(이전에) + Handle(처리한다) = 컨트롤러에 도달하기 전에, 미리 처리하는 메서드
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // HttpServletRequest request: 클라이언트(브라우저, 사용자)가 보낸 HTTP 요청 메시지 전체를 객체화한 것
        // HttpServletResponse response: 서버가 클라이언트에게 보낼 HTTP 응답 메시지를 객체화한 것, 페이지를 이동시킬 때 사용.
        // Object handler: 현재 요청을 처리하기로 매핑된(정해진) 대상 컨트롤러 객체(Bean) 혹은 메서드 정보.

        // 클라이언트가 요청한 주소 문자열 추출, EX) /admin/dashboard.do)
        String requestURI = request.getRequestURI();

        // 서버 메모리에 있는 사용자의 세션 객체(HttpSession)를 조회.
        HttpSession session = request.getSession(false);

        // 로그인 정보 조회: 세션이 null 아니면 사용자 정보 가져오고, 세션이 없으면 null 반환
        MemberDTO loginMember = (session != null) ? (MemberDTO) session.getAttribute("LOGIN_MEMBER") : null;

        // 1. 로그인 필요 URL인데 로그인 안 한 경우
        if (loginMember == null) {
            // .sendRedirect: HttpServletResponse 인터페이스 메서드: 요청한 작업을 중단하고, 파라미터의 주소로 다시 접속하게 함.
            response.sendRedirect("/login.do");
            return false; // 요청 차단
        }

        // 2. 관리자 전용 URL 접근 시 role 검사
        // .startsWith: java lang 기본 메서드: 문자열이 특정 접두사(Prefix)로 시작하는지 검사하여 결과를 boolean으로 반환
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
