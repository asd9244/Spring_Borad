package com.board.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDTO extends CommonDTO {

    private Long idx; //글 번호(PK)
    private String title; // 글 제목
    private String content; // 내용
    private String writer; // 작성자
    private int viewCnt; //조회수
    private String noticeYn; // 공지여부
    private String secretYn; // 비밀벼부

    // lombok을 사용해서 getter/setter는 생략.
}
