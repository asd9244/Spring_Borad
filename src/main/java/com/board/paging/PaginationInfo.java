package com.board.paging;

public class PaginationInfo {

    private Criteria criteria; // 페이지 번호 계산에 필요한 Criteria 클래스의 멤버 변수들에 대한 정보를 가지는 변수
    private int totalRecordCount; // 전체 데이터의 개수
    private int totalPageCount; // 전체 페이지 개수
    private int firstPage; // pageSize = 10일때 currentPage = 3이면 firstPage = 1을 의미
    private int lastPage; // pageSize = 10일때 currentPage = 3이면 lastPage = 10을 의미
    private int firstRecordIndex; // Criteria 클래스의 getStartPage 메서드를 대체해서 LIMIT구문의 첫 번째 값에 사용되는 변수
    private int lastRecordIndex; // 오라클과 같이 LIMIT 구문이 존재하지 않는 DB용. mySQL기반이라 사용하지 않음
    private boolean hasPreviousPage; // 이전 페이지가 존재하는 지를 판단하기 위한 변수
    private boolean hasNextPage; // 다음 페이지가 존재하는 지를 구분하는 용도


    public PaginationInfo(Criteria criteria) { //  잘못된 값이 들어왔을 때, 기본값 지정, 페이징 번호 계산
        if (criteria.getCurrentPageNo() < 1) {
            criteria.setCurrentPageNo(1);
        }
        if (criteria.getRecordsPerPage() < 1 || criteria.getRecordsPerPage() > 100) {
            criteria.setRecordsPerPage(10);
        }
        if (criteria.getPageSize() < 5 || criteria.getPageSize() > 20) {
            criteria.setPageSize(10);
        }
        this.criteria = criteria;
    }

    private void calculation() {
        /* 전체 페이지 수 (현재 페이지 번호가 전체 페이지 수보다 크면 현재 페이지 번호에 전체 페이지 수를 저장) */
        // ( (전체 데이터 개수 - 1) / 페이지당 출력할 데이터 개수 ) + 1을 통해 전체 페이지 개수 산출
        totalPageCount = ((totalRecordCount - 1) / criteria.getRecordsPerPage()) + 1;
        if (criteria.getCurrentPageNo() > totalPageCount) {
            criteria.setCurrentPageNo(totalPageCount);
        }

        /* 페이지 리스트의 첫 페이지 번호 */
        // ( (현재 페이지 번호 - 1) / 화면 하단의 페이지 개수 ) * 화면 하단의 페이지 개수 + 1을 통해 가장 좌측의 페이지 번호 산출
        // ex) (currentPageNo(13) -1) / pageSize(10) = 1, 1 * pageSize(10) = 10, 10 + 1 = firstPage(11)
        firstPage = ((criteria.getCurrentPageNo() - 1) / criteria.getPageSize()) * criteria.getPageSize() + 1;

        /* 페이지 리스트의 마지막 페이지 번호 (마지막 페이지가 전체 페이지 수보다 크면 마지막 페이지에 전체 페이지 수를 저장) */
        // (첫 페이지 번호 + 화면 하단의 페이지 개수) - 1을 통해 마지막 페이지 번호 산출
        lastPage = firstPage + criteria.getPageSize() - 1;
        if (lastPage > totalPageCount) {
            lastPage = totalPageCount;
        }

        /* SQL의 조건절에 사용되는 첫 RNUM */
        firstRecordIndex = (criteria.getCurrentPageNo() - 1) * criteria.getRecordsPerPage();

        /* SQL의 조건절에 사용되는 마지막 RNUM */
        lastRecordIndex = criteria.getCurrentPageNo() * criteria.getRecordsPerPage();

        /* 이전 페이지 존재 여부 */
        hasPreviousPage = firstPage != 1;

        /* 다음 페이지 존재 여부 */
        hasNextPage = (lastPage * criteria.getRecordsPerPage()) < totalRecordCount;

    }

    public Criteria getCriteria() {
        return criteria;
    }

    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
    }

    public int getTotalRecordCount() {
        return totalRecordCount;
    }

    // 파라미터로 넘어온 전체 데이터 개수를 PaginationInfo 클래스의 전체 데이터 개수에 저장
    // 전체 데이터 개수가 1개 이상이면 페이지 번호를 계산하는 calculation 메서드 실행
    public void setTotalRecordCount(int totalRecordCount) {
        this.totalRecordCount = totalRecordCount;

        if (totalRecordCount > 0) {
            calculation();
        }
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public int getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(int firstPage) {
        this.firstPage = firstPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public int getFirstRecordIndex() {
        return firstRecordIndex;
    }

    public void setFirstRecordIndex(int firstRecordIndex) {
        this.firstRecordIndex = firstRecordIndex;
    }

    public int getLastRecordIndex() {
        return lastRecordIndex;
    }

    public void setLastRecordIndex(int lastRecordIndex) {
        this.lastRecordIndex = lastRecordIndex;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }
}
/* Getter Setter 생성 */