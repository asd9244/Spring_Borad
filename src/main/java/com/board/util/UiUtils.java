package com.board.util;

import com.board.constant.Method;
import com.board.paging.Criteria;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class UiUtils {

    public String showMessageWithRedirect(@RequestParam(value = "message", required = false) String message, // 사용자에게 전달할 메시지
                                          @RequestParam(value = "redirectUri", required = false) String redirectUri, // 게시글작성 -> 등록완료메시지 전달 -> 리스트페이즈로 리다이렉트
                                          @RequestParam(value = "method", required = false) Method method, // Method Enum 클래스에 선언한 HTTP 요청 메서드
                                          // 파라미터의 개수가 페이지마다 변동되므로, 여러 가지 데이터를 Key, Value 형태로 담을 수 있는 Map 활용.
                                          @RequestParam(value = "params", required = false) Map<String, Object> params, Model model) {// params: 화면으로 전달할 파라미터
        model.addAttribute("message", message);
        model.addAttribute("redirectUri", redirectUri);
        model.addAttribute("method", method);
        model.addAttribute("params", params);

        return "utils/message-redirect";
    }

    // Criteria 클래스의 모든 멤버 변수(이전 페이지 정보)를 Map에 Key, Value 형태 리턴
    public Map<String, Object> getPagingParams(Criteria criteria) {
        // GET 방식이 아닌 POST 방식의 처리에서만 사용
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("currentPageNo", criteria.getCurrentPageNo());
        params.put("recordsPerPage", criteria.getRecordsPerPage());
        params.put("pageSize", criteria.getPageSize());
        params.put("searchType", criteria.getSearchType());
        params.put("searchKeyword", criteria.getSearchKeyword());

        return params;
    }
}
