package com.board.interceptor;

import com.board.domain.MemberDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.PrintWriter;

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

        // 로그인 필요 URL인데 로그인 안 한 경우
        if (loginMember == null) {
            // AJAX 요청인지 확인 (AJAX라면 로그인 페이지 HTML 대신 401 에러를 반환해야 함)
            if (isAjaxRequest(request)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED); // .SC_UNAUTHORIZED = 401 Unauthorized에러 반환.
                return false; // 요청 차단
            }

            // 현재 페이지 정보를 세션에 저장 (로그인 후 다시 돌아오기 위함)
            // 단, 로그인 페이지나 에러 페이지 등이 아닐 경우에만 저장
            // 1. 현재 사용자가 들어가려던 주소 뒤에 붙은 파라미터(?idx=10 같은 것)를 가져온다
            String queryString = request.getQueryString();

            // 2. 전체 주소를 조립합니다. (예: /board/view.do + ? + idx=10)
            // 파라미터가 없으면 그냥 주소만, 있으면 합쳐서 저장합니다.
            String targetUrl = (queryString != null) ? requestURI + "?" + queryString : requestURI;
            
            // 3. GET 요청일 때만 저장 (POST 데이터는 복구 불가)
            if (request.getMethod().equals("GET")) { 
                // 4. 세션(서버 쪽 메모장)에 "targetUrl"이라는 이름으로 이 주소를 적어둡니다
                request.getSession(true).setAttribute("targetUrl", targetUrl);
            }

            // 5. .sendRedirect: HttpServletResponse 인터페이스 메서드: 요청한 작업을 중단하고, 파라미터의 주소로 다시 접속하게 함.
            response.sendRedirect("/login.do");
            return false; // 요청 차단
        }

        // 관리자 전용 URL 접근 시 role 검사
        // .startsWith: java lang 기본 메서드: 문자열이 특정 접두사(Prefix)로 시작하는지 검사하여 결과를 boolean으로 반환
        if (requestURI.startsWith("/admin")) {
            if (!"ADMIN".equals(loginMember.getRole())) { // ADMIN 권한이 아니면 접근 차단
                // 1. 응답 데이터 타입 설정
                // 브라우저가 응답받은 데이터를 HTML 문서로 인식하고, 한글이 깨지지 않도록(UTF-8) 인코딩을 설정합니다.
                response.setContentType("text/html; charset=UTF-8");
                
                // 2. 출력 스트림(PrintWriter) 객체 획득
                // HttpServletResponse 객체로부터 텍스트 데이터를 클라이언트로 전송하기 위한 출력 스트림을 가져옵니다.
                PrintWriter out = response.getWriter();
                
                // 3. 클라이언트로 스크립트 코드 전송
                // 스트림을 통해 HTML <script> 태그 문자열을 브라우저로 전송합니다.
                // 브라우저는 이 문자열을 수신하는 즉시 자바스크립트를 실행합니다.
                // - alert(...): 경고 메시지 출력
                // - location.href: 브라우저의 주소를 변경하여 페이지 이동
                out.println("<script>alert('접근 권한이 없는 페이지입니다.'); location.href='/board/list.do';</script>");
                
                // 4. 버퍼 비우기 및 전송 완료
                out.flush();
                
                return false; // 컨트롤러 실행 차단
            }
        }

        // 모든 조건 통과 → 요청 진행
        return true;
    }

    // AJAX 요청 판별 헬퍼 메서드
    private boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(header);
    }
}
