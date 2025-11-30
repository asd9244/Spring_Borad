package com.board.paging;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class Criteria {

    private int currentPageNo; // 현재 페이지 번호
    private int recordsPerPage; // 페이지당 출력할 데이터 개수
    private int pageSize; // 화면 하단에 출력할 페이지 사이즈(ex)1~5, 6~10)
    private String searchKeyword; // 검색 키워드
    private String searchType; // 검색 유형 (전체검색, 제목, 내용, 작성자 검색 등)

    public Criteria() {
        this.currentPageNo = 1; // 화면을 처리할 때 페이징 정보를 계산하는 용도로 사용되는 변수
        this.recordsPerPage = 10; // 화면 처리에서 페이징 정보 계산에 사용
        this.pageSize = 10; //  10으로 지정하면 1~10까지의 페이지가 출력
    }

    public String makeQueryString(int pageNo) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance() // UriComponentsBuilder: spring 유틸 클레스: builder 객체 생성.
                .queryParam("currentPageNo", pageNo) // queryParam(name, value): ?key=value 형태의 파라미터를 추가
                .queryParam("recordsPerPage", recordsPerPage)
                .queryParam("pageSize", pageSize)
                .queryParam("searchType", searchType)
                .queryParam("searchKeyword", searchKeyword)
                // .build()메서드: 설정한 파라미터들로 UriComponents 객체를 완성.
                .build()
                .encode(); // 한글이나 특수문자가 깨지지 않도록 인코딩(UTF-8 등) 처리.

        return uriComponents.toUriString(); // 이 메서드를 실행하면 ?currentPageNo=2&recordsPerPage=10&searchType=title... 같은 문자열이 생성됨.
    }

    // MySQL의 LIMIT 구문에서 데이터를 어디서부터 가져올지 결정하는 **시작점(Offset)**을 계산
    public int getStartPage() { // MySQL에서 LIMIT 구문의 앞부분에 사용되는 메서드
        return (currentPageNo - 1) * recordsPerPage;
    }

    public int getCurrentPageNo() {
        return currentPageNo;
    }

    public void setCurrentPageNo(int currentPageNo) {
        this.currentPageNo = currentPageNo;
    }

    public int getRecordsPerPage() {
        return recordsPerPage;
    }

    public void setRecordsPerPage(int recordsPerPage) {
        this.recordsPerPage = recordsPerPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    @Override
    public String toString() {
        return "Criteria{" +
                "currentPageNo=" + currentPageNo +
                ", recordsPerPage=" + recordsPerPage +
                ", pageSize=" + pageSize +
                ", searchKeyword='" + searchKeyword + '\'' +
                ", searchType='" + searchType + '\'' +
                '}';
    }
}