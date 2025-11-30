package com.board.controller;

import com.board.constant.Method;
import com.board.domain.BoardDTO;
import com.board.service.BoardService;
import com.board.util.UiUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller // Controller는 사용자의 요청(URI)을 처리하고, 결과를 담아 View(화면)를 반환하는 역할을 합니다.
public class BoardController extends UiUtils {


    @Autowired // 스프링이 BoardService 인터페이스의 구현체인 BoardServiceImpl을 변수에 주입.
    private BoardService boardService; // 이 클레스에서는 BoardServiceImpl의 메소드를 사용 가능.

    // 게시글 작성 or 수정 화면을 열어주는 메서드.
    @GetMapping(value = "/board/write.do")// 사용자가 이 주소로 접근하면 아래 메서드 실행.
    public String openBoardWrite(@ModelAttribute("params") BoardDTO params, @RequestParam(value = "idx", required = false) Long idx, Model model) {// 파라미터값으로 idx를 받지만 없어도 에러안남.

        if (idx == null) { // idx가 없으면 새 게시글 작성화면 출력
            model.addAttribute("board", new BoardDTO());
        } else {
            BoardDTO board = boardService.getBoardDetail(idx);
            if (board == null || "Y".equals(board.getDeleteYn())) {// idx가 있지만 글이 없으면(삭제 대기중 or 사용자가 직접 url에 idx를 찍고 들어오는 경우) 글목록 출력
                return showMessageWithRedirect("없는 게시글이거나 이미 삭제된 게시글입니다.", "/board/list.do", Method.GET, null, model);
            } // idx도 있고 글 내용도 있으면 수정페이지 출력
            model.addAttribute("board", board);
        }
        return "board/write";// board/write 라는 이름의 html파일을 반환
    }

    //     게시글 등록(글쓰기)
    @PostMapping(value = "/board/register.do")
    public String registerBoard(@ModelAttribute("params") final BoardDTO params, Model model) {
        Map<String, Object> pagingParams = getPagingParams(params);
        try {
            boolean isRegistered = boardService.registerBoard(params);
            if (isRegistered == false) {
                return showMessageWithRedirect("게시글 등록에 실패하였습니다.", "/board/list.do", Method.GET, pagingParams, model);
            }
        } catch (DataAccessException e) {
            return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/board/list.do", Method.GET, pagingParams, model);
        } catch (Exception e) {
            return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/board/list.do", Method.GET, pagingParams, model);
        }
        return showMessageWithRedirect("게시글 등록이 완료되었습니다.", "/board/list.do", Method.GET, pagingParams, model);
    }


    // 게시글 목록 처리
    @GetMapping(value = "/board/list.do") // GET방식의 HTTP요청 메서드
    public String openBoardList(@ModelAttribute("params") BoardDTO params, HttpServletRequest request, Model model) { // ModelAttribute == 파라미터로 전달받은 객체를 자동으로 뷰까지 전달
        List<BoardDTO> boardList = boardService.getBoardList(params);// 전체 게시글 데이터를 boardList객체에 할당
        // addAttribute메서드가 객체에 "  "라는 속성(이름)을 부여하고 model객체에 넣음.
        // requestURI == 현제 URI경로를 model객체에 넣음. 이 경로는 페이징 버튼 등을 만들 때 사용 가능.
        model.addAttribute("requestURI", request.getRequestURI());
        model.addAttribute("boardList", boardList);
        return "board/list"; //list.html에서는 model객체에서 boardList라는 속성을 가진 객체를 찾아서 사용.
    }

    // 게시글 조회 처리
    @GetMapping(value = "/board/view.do")
    public String openBoardDetail(@ModelAttribute("params") BoardDTO params, @RequestParam(value = "idx", required = false) Long idx, Model model) {
        if (idx == null) { // 입력창에 게시글 번호조차 없다면, 메시지팝업, 리스트로 되돌아감.
            return showMessageWithRedirect("올바르지 않은 접근입니다.", "/board/list.do", Method.GET, null, model);
        }
        BoardDTO board = boardService.getBoardDetail(idx); // 파라미터로 입력받은 게시글 번호로 게시글정보 가져오기.
        boardService.viewCntUP(board);
        if (board == null || "Y".equals(board.getDeleteYn())) { // 받아온 idx로 게시글 조회해서 없거나, 삭제되었으면 리스트로
            // 입력창에 게시글 번호는 맞게 입력했지만, 이미 삭제되었다면, 메시지팝업, 리스트로 되돌아감.
            return showMessageWithRedirect("없는 게시글이거나 이미 삭제된 게시글입니다.", "/board/list.do", Method.GET, null, model);
        }
        model.addAttribute("board", board); // 게시글번호도 있고, 게시글도 남아있다면, 게시글 정보를 모델에 담는다.
        return "board/view";
    }

    // 게시물 삭제
    @PostMapping(value = "/board/delete.do")
    public String deleteBoard(@ModelAttribute("params") BoardDTO params, @RequestParam(value = "idx", required = false) Long idx, Model model) {
        if (idx == null) {  // ModelAttribute: 이전 페이지 정보가 담겨있는 Criteria 클래스를 상속받는 BoardDTO 커맨드 객체를 화면(View)으로 전달
            return showMessageWithRedirect("올바르지 않은 접근입니다.", "/board/list.do", Method.GET, null, model);
        }

        // UIUtils 클래스에 추가한 getPagingParams 메서드를 호출
        Map<String, Object> pagingParams = getPagingParams(params); // pagingParams에 담긴 이전 페이지 정보를 showMessageWithRedirect 메서드의 인자로 전달
        try {
            boolean isDeleted = boardService.deleteBoard(idx);
            if (isDeleted == false) {
                return showMessageWithRedirect("게시글 삭제에 실패하였습니다.", "/board/list.do", Method.GET, pagingParams, model);
            }
        } catch (DataAccessException e) {
            return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/board/list.do", Method.GET, pagingParams, model);
        } catch (Exception e) {
            return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/board/list.do", Method.GET, pagingParams, model);
        }
        return showMessageWithRedirect("게시글 삭제가 완료되었습니다.", "/board/list.do", Method.GET, pagingParams, model);
    }


}
