package com.board.controller;

import com.board.domain.BoardDTO;
import com.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller // Controller는 사용자의 요청(URI)을 처리하고, 결과를 담아 View(화면)를 반환하는 역할을 합니다.
public class BoardController {

    @Autowired // 스프링이 BoardService 인터페이스의 구현체인 BoardServiceImpl을 변수에 주입.
    private BoardService boardService; // 이 클레스에서는 BoardServiceImpl의 메소드를 사용 가능.

    // 게시글 작성 or 수정 화면을 열어주는 메서드.
    @GetMapping(value = "/board/write.do")// 사용자가 이 주소로 접근하면 아래 메서드 실행.
    public String openBoardWrite(@RequestParam(value = "idx", required = false) Long idx, Model model) {// 파라미터값으로 idx를 받지만 없어도 에러안남.

        if (idx == null) { // idx가 없으면 새 게시글 작성화면 출력
            model.addAttribute("board", new BoardDTO());
        } else {
            BoardDTO board = boardService.getBoardDetail(idx);
            if (board == null) {// idx가 있지만 글이 없으면(삭제 대기중 or 사용자가 직접 url에 idx를 찍고 들어오는 경우) 글목록 출력
                return "redirect:/board/list.do";
            } // idx도 있고 글 내용도 있으면 수정페이지 출력
            model.addAttribute("board", board);
        }
        return "board/write";// board/write 라는 이름의 html파일을 반환
    }

    // 게시글 등록
    @PostMapping(value = "/board/register.do")
    public String registerBoard(final BoardDTO params) {
        try {
            boolean isRegistered = boardService.registerBoard(params);
            if (isRegistered == false) {
                // TODO => 게시글 등록에 실패했다는 메시지 전달.
            }
        } catch (DataAccessException e) {
            // TODO => 데이터 베이스 처리 과정에 문제가 발생했다는 메시지 전달.
        } catch (Exception e) {
            // TODO => 시스템에 문제가 발생했다는 메시지 전달.
        }
        return "redirect:/board/list.do";
    }

    // 게시글 목록 처리
    @GetMapping(value = "/board/list.do") // GET방식의 HTTP요청 메서드
    public String openBoardList(Model model) {
        List<BoardDTO> boardList = boardService.getBoardList();// 전체 게시글 데이터를 boardList객체에 할당
        // addAttribute메서드가 boardList객체에 "boardList"라는 속성(이름)을 부여하고 model객체에 넣음.
        model.addAttribute("boardList", boardList);
        return "board/list"; //list.html에서는 model객체에서 boardList라는 속성을 가진 객체를 찾아서 사용.
    }

    // 게시글 조회 처리
    @GetMapping(value = "/board/view.do")
    public String openBoardDetail(@RequestParam(value = "idx", required = false) Long idx, Model model) {
        if (idx == null) {
            // TODO => 올바르지 않은 접근이라는 메시지를 전달하고, 게시글 리스트로 리다이렉트
            return "redirect:/board/list.do";
        }
        BoardDTO board = boardService.getBoardDetail(idx);
        if (board == null || "Y".equals(board.getDeleteYn())) { // 받아온 idx로 게시글 조회해서 없거나, 삭제되었으면 리스트로
            // TODO => 없는 게시글이거나, 이미 삭제된 게시글이라는 메시지를 전달하고, 게시글 리스트로 리다이렉트
            return "redirect:/board/list.do";
        }
        model.addAttribute("board", board);
        return "board/view";
    }

    @PostMapping(value = "/board/delete.do")
    public String deleteBoard(@RequestParam(value = "idx", required = false) Long idx) {
        if (idx == null) {
            // TODO => 올바르지 않은 접근이라는 메시지를 전달하고, 게시글 리스트로 리다이렉트
            return "redirect:/board/list.do";
        }
        try {
            boolean isDeleted = boardService.deleteBoard(idx);
            if (isDeleted == false) {
                // TODO => 게시글 삭제에 실패하였다는 메시지를 전달
            }
        } catch (DataAccessException e) {
            // TODO => 데이터베이스 처리 과정에 문제가 발생하였다는 메시지를 전달.
        } catch (Exception e) {
            // TODO =>
        }
        return "redirect:/board/list.do";
    }


}
