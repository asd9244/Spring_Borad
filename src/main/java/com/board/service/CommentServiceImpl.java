package com.board.service;

import com.board.domain.CommentDTO;
import com.board.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service // 비즈니스 로직을 수행하는 클래스임을 선언
public class CommentServiceImpl implements CommentService {

    @Autowired  // DB와 연결할 수 있는 CommentMapper 의존성 주입
    private CommentMapper commentMapper;

    @Override
    public boolean registerComment(CommentDTO params) { // 댓글 번호(idx)가 파라미터에 포함되어 있지 않으면 INSERT를 실행하고, 포함되어 있으면 UPDATE를 실행
        int queryResult = 0;

        if (params.getIdx() == null) {
            queryResult = commentMapper.insertComment(params);
        } else {
            queryResult = commentMapper.updateComment(params);
        }

        return (queryResult == 1) ? true : false;
    }

    @Override
    public boolean deleteComment(Long idx) { // 댓글의 상세 정보를 조회해서 정상적으로 사용 중인 댓글인 경우에만 삭제
        int queryResult = 0;

        CommentDTO comment = commentMapper.selectCommentDetail(idx);

        if (comment != null && "N".equals(comment.getDeleteYn())) {
            queryResult = commentMapper.deleteComment(idx);
        }

        return (queryResult == 1) ? true : false;
    }

    @Override
    public List<CommentDTO> getCommentList(CommentDTO params) { // 특정 게시글에 포함된 댓글이 1개 이상이면 댓글 목록 반환

        List<CommentDTO> commentList = Collections.emptyList();

        int commentTotalCount = commentMapper.selectCommentTotalCount(params);
        if (commentTotalCount > 0) {
            commentList = commentMapper.selectCommentList(params);
        }

        return commentList;
    }

}