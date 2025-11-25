package com.board.service;

import com.board.domain.BoardDTO;
import com.board.mapper.BoardMapper;
import com.board.paging.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service // 비즈니스 로직을 담당하는 서비스 클래스 선언
public class BoardServiceImpl implements BoardService {

    @Autowired // DM와 연결할 수 있는 boardMapper 의존성 주입
    private BoardMapper boardMapper; // boardMapper를 통해 DM에 명령할 수 있다.

    // 게시글 생성 및 수정 처리
    @Override
    public boolean registerBoard(BoardDTO params) {
        int queryResult = 0;

        if (params.getIdx() == null) { // 게시글 번호가 없으면, 게시글 생성
            queryResult = boardMapper.insertBoard(params);
        } else { // 게시글 번호가 있으면? 게시글 수정
            queryResult = boardMapper.updateBoard(params);
        }
        // 작업이 성공하면 true(1), 실패하면 false(0) 반환.
        return (queryResult == 1) ? true : false;
    }

    // 게시글 하나 조회
    @Override
    public BoardDTO getBoardDetail(Long idx) {
        return boardMapper.selectBoardDetail(idx);
    } // idx값을 받아서, 값과 일치하는 게시글을 반환.

    // 게시글 삭제
    @Override
    public boolean deleteBoard(Long idx) {
        int queryResult = 0;
        // 1. 먼저 idx값을 받아서 board객체에 게시글을 할당.
        BoardDTO board = boardMapper.selectBoardDetail(idx);
        // 2. board객체에 게시글이 존재하고 && 삭제상태가 아니라면,
        if (board != null && "N".equals(board.getDeleteYn())) {
            queryResult = boardMapper.deleteBoard(idx); // 3. mapper에게 삭제 명령
        }
        // 4. 삭제성공 true(1), 실패 false(0)
        return (queryResult == 1) ? true : false;
    }

    // 전체 게시글 조회
    @Override
    public List<BoardDTO> getBoardList(Criteria criteria) {
        List<BoardDTO> boardList = Collections.emptyList(); // 1. 게시글 목록을 저장할 boardList 변수 선언
        // 2. 빈, 변경불가능한 List 할당
        int boardTotalCount = boardMapper.selectBoardTotalCount(criteria);// 3. 삭제여부가 N인 게시물 개수 조회

        if (boardTotalCount > 0) {// 4. 게시물 개수가 0보다 크면 boardList에 게시물 할당.
            boardList = boardMapper.selectBoardList(criteria);
        }
        return boardList;
    }

}