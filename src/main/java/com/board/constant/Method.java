package com.board.constant;

public enum Method {
    // enum Class: HTTP 요청 메서드 이름을 안전하게 다루기 위한 전용 타입을 만든 것
    // String으로 선언할 경우 메서드 호출코드에 오타가 나면 그대로 에러 발생.
    // enum이면 잘못 실행될 경우 컴파일 단계에서 에러가 발생해서 타입 안정성이 확보됨.
    // 일반적으로 사용되는 HTTP 메서드 5종
    GET, POST, PUT, PATCH, DELETE;
}
