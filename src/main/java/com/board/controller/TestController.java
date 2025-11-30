package com.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

//■ REST(Representational State Transfer)
//• 하나의 URI는 하나의 고유한 리소스(Resource)를 대표하도록 설계된다는 개념
//• 디바이스의 종류에 상관없이 공통으로 데이터를 처리할 수 있도록 하는 방식
//■ REST API
//• 사용자가 어떠한 요청을 했을 때 화면(HTML)을 리턴하지 않고,
//• 사용자가 필요로 하는 결과(데이터)만을 리턴해주는 방식

@Controller
public class TestController { // 주소창에 http://localhost:8080/members 입력하면, html파일이 아닌 데이터 자체를 화면에 출력함.

    @GetMapping(value = "/members")
    @ResponseBody // public @ResponseBody String testByResponseBody()와 같이 리턴타입 좌측에 지정 가능
    public Map<Integer, Object> testByResponseBody() {

        Map<Integer, Object> members = new HashMap<>();

        for (int i = 1; i <= 20; i++) {
            Map<String, Object> member = new HashMap<>();
            member.put("idx", i);
            member.put("nickname", i + "바보");
            member.put("height", i + 20);
            member.put("weight", i + 30);
            members.put(i, member);
        }

        return members;
    }
}
