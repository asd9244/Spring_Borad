package com.board.domain;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDTO {

    private Long idx; //글 번호(PK)
    private String title; // 글 제목
    private String content; // 내용
    private String writer; // 작성자
    private int viewCnt; //조회수
    private String noticeYn; // 공지여부
    private String secretYn; // 비밀벼부
    private String deleteYn; // 삭제여부
    private LocalDate insertTime; // 등록일
    private LocalDate updateTime; // 수정일
    private LocalDate deleteTime; // 삭제일


    public Long getIdx() {
        return idx;
    }
    public void setIdx(Long idx) {
        this.idx = idx;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getWriter() {
        return writer;
    }
    public void setWriter(String writer) {
        this.writer = writer;
    }
    public int getViewCnt() {
        return viewCnt;
    }
    public void setViewCnt(int viewCnt) {
        this.viewCnt = viewCnt;
    }
    public String getNoticeYn() {
        return noticeYn;
    }
    public void setNoticeYn(String noticeYn) {
        this.noticeYn = noticeYn;
    }
    public String getSecretYn() {
        return secretYn;
    }
    public void setSecretYn(String secretYn) {
        this.secretYn = secretYn;
    }
    public String getDeleteYn() {
        return deleteYn;
    }
    public void setDeleteYn(String deleteYn) {
        this.deleteYn = deleteYn;
    }
    public LocalDate getInsertTime() {
        return insertTime;
    }
    public void setInsertTime(LocalDate insertTime) {
        this.insertTime = insertTime;
    }
    public LocalDate getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(LocalDate updateTime) {
        this.updateTime = updateTime;
    }
    public LocalDate getDeleteTime() {
        return deleteTime;
    }
    public void setDeleteTime(LocalDate deleteTime) {
        this.deleteTime = deleteTime;
    }
}
