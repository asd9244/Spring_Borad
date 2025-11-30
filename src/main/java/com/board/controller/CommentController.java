package com.board.controller;

import com.board.domain.CommentDTO;
import com.board.service.CommentService;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // 선언된 컨트롤러의 모든 메서드는 화면이 아닌, 리턴 타입에 해당하는 데이터 자체를 리턴
public class CommentController {

    @Autowired
    private CommentService commentService;

    // @PathVariable
    // @RequestParam과 유사한 기능을 하며, REST 방식에서 리소스를 표현할 때 사용
    // @PathVariable은 URI에 파라미터로 전달받을 변수 지정
    // "/comments/{boardIdx}" URI의 {boardIdx}는 게시글 번호를 의미하며, @PathVariable의 boardIdx와 매핑(바인딩)
    @GetMapping(value = "/comments/{boardIdx}") // @GetMapping: HTTP GET요청만 처리하도록 지정.
    public List<CommentDTO> getCommentList(@PathVariable("boardIdx") Long boardIdx, @ModelAttribute("params") CommentDTO params) {
        List<CommentDTO> commentList = commentService.getCommentList(params);
        return commentList;
    }

    // @RequestMapping: 게시글의 경우, 하나의 URI로 생성(INSERT)과 수정(UPDATE) 처리가 가능하지만, REST API는 설계 규칙을 지켜야 하기 때문에 URI를 구분함
    // value = {"/comments": 새로운 댓글 등록 , "/comments/{idx}": 댓글 테이블의 PK인 댓글 번호(idx)에 해당하는 댓글 수정}
    // method: {RequestMethod.POST: HTTP 요청 메서드 중 POST를 의미하며, @PostMapping과 유사 ,,,, RequestMethod.PATCH: HTTP 요청 메서드 중 PATCH를 의미하며, @PatchMapping과 유사}
    // @RequestBody 작동방식
    // 파라미터 앞에 @RequestBody가 지정되면, 파라미터로 전달받은 JSON 문자열을 객체로 변환
    //• 1. 클라이언트(사용자)는 게시글 번호, 댓글 내용, 댓글 작성자를 JSON 문자열로 전송한다.
    //• 2. 서버(컨트롤러)는 JSON 문자열을 파라미터로 전달받는다.
    //• 3. @RequestBody는 전달받은 JSON 문자열을 객체로 변환한다.
    //• 4. 객체로 변환된 JSON은 CommentDTO 클래스의 객체인 params에 매핑(바인딩)된다.
    @RequestMapping(value = {"/comments", "/comments/{idx}"}, method = {RequestMethod.POST, RequestMethod.PATCH})
    public JsonObject registerComment(@PathVariable(value = "idx", required = false) Long idx, @RequestBody final CommentDTO params) {
        JsonObject jsonObj = new JsonObject();

        try {// 댓글의 생성 또는 수정이 실행되면 true를, 실행되지 않으면 false를 저장

            boolean isRegistered = commentService.registerComment(params);
            jsonObj.addProperty("result", isRegistered);

        } catch (DataAccessException e) {
            jsonObj.addProperty("message", "데이터베이스 처리 과정에 문제가 발생하였습니다.");

        } catch (Exception e) {
            jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다.");
        }

        return jsonObj;

    }

    // HTTP 요청 메서드 중 DELETE를 의미, 실제로 댓글을 삭제하지 않지만, URI의 구분을 위해 @DeleteMapping을 선언
    // 1. JSON 객체 생성
    // 2. CommentService의 deleteComment 메서드의 실행 결과를 JSON 객체에 저장
    // 3. 각 catch 문에 해당되는 예외가 발생하면 예외 메시지를 JSON 객체에 저장
    // 4. JSON 객체를 리턴
    @DeleteMapping(value = "/comments/{idx}") //  URI의 {idx}는 댓글 번호(PK)를 의미
    public JsonObject deleteComment(@PathVariable("idx") final Long idx) { // @PathVariable은 URI에 파라미터로 전달받을 변수를 지정
        JsonObject jsonObj = new JsonObject();

        try {
            boolean isDeleted = commentService.deleteComment(idx);
            jsonObj.addProperty("result", isDeleted);

        } catch (DataAccessException e) {
            jsonObj.addProperty("message", "데이터베이스 처리 과정에 문제가 발생하였습니다.");

        } catch (Exception e) {
            jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다.");
        }

        return jsonObj;
    }
}

