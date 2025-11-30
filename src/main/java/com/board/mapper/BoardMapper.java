package com.board.mapper;

import com.board.domain.BoardDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper //데이터베이스와 통신하기 위한 어노테이션: SQL문을 찾아 실행
public interface BoardMapper {

    // (Create, Update, Delete) Method Return Type: int
    // 게시글을 데이터베이스에 **삽입(Create)**하고, 작업 성공 시 영향을 받은 행의 수(1)를 반환합니다.
    public int insertBoard(BoardDTO params);

    // 특정 idx에 해당하는 게시글 하나를 데이터베이스에서 **조회(Read)**하여 BoardDTO 객체로 반환합니다.
    public BoardDTO selectBoardDetail(Long idx);

    // 게시글 내용을 **수정(Update)**하고, 작업 성공 시 영향을 받은 행의 수(1)를 반환합니다.
    public int updateBoard(BoardDTO params);

    //    특정 idx에 해당하는 게시글을 삭제 처리하고, 작업 성공 시 영향을 받은 행의 수(1)를 반환합니다.
    public int deleteBoard(Long idx);

    // **검색 조건(Criteria)**에 맞는 게시글 목록을 조회합니다. 페이징 처리 정보(시작 위치, 개수)도 이 객체를 통해 전달받습니다.
    public List<BoardDTO> selectBoardList(BoardDTO params);

    // **검색 조건(Criteria)**에 맞는 전체 게시글의 개수를 조회하여 페이징 계산에 활용합니다.
    public int selectBoardTotalCount(BoardDTO params);

    // 게시글의 조회수를 증가시킨다.
    public int viewCntUP(BoardDTO params);

}
