package com.board.mapper;

import java.util.List;
import com.board.domain.BoardDTO;
import org.apache.ibatis.annotations.Mapper;


@Mapper //데이터베이스와 통신하기 위한 어노테이션: SQL문을 찾아 실행
public interface BoardMapper {

    // (Create, Update, Delete) Method Return Type: int
    public int insertBoard(BoardDTO params); // INSERT 쿼리 호출 메서드: 게시글 생성
    public int updateBoard(BoardDTO params); // UPDATE 쿼리 호출 메서드: 게시글 하나 조회
    public int deleteBoard(Long idx); // DELETE 쿼리 호출 메서드: 게시글 삭제
    public int selectBoardTotalCount(); // SELECT 쿼리 호출 메서듸 삭제 여부(delete_yn)가 'N'으로 지정된 게시글 개수 조회.
    public BoardDTO selectBoardDetail(Long idx); // SELECT 쿼리 호출 메서드: 하나의 게시글 조회
    public List<BoardDTO> selectBoardList(); // SELECT 쿼리 호출 메서드: 게시글 목록 조회


}
