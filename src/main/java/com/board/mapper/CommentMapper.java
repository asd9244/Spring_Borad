package com.board.mapper;

import com.board.domain.CommentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper //데이터베이스와 통신하기 위한 어노테이션: SQL문을 찾아 실행
public interface CommentMapper {
    // 댓글을 삽입하는 INSERT 쿼리 호출
    public int insertComment(CommentDTO params);

    // 파라미터로 전달받은 댓글 번호에 해당하는 댓글의 상세 내용을 조회
    public CommentDTO selectCommentDetail(Long idx);

    // 댓글을 수정하는 UPDATE 쿼리 호출
    public int updateComment(CommentDTO params);

    // 댓글을 삭제하는 메서드. DELETE 쿼리를 호출하지 않고, UPDATE 쿼리를 호출해서 delete_yn 컬럼의 상태를 변경
    public int deleteComment(Long idx);

    // 특정 게시글에 포함된 댓글 목록을 조회하는 SELETE 쿼리 호출
    public List<CommentDTO> selectCommentList(CommentDTO params);

    // 특정 게시글에 포함된 댓글 개수를 조회하는 SELETE 쿼리 호출
    public int selectCommentTotalCount(CommentDTO params);

}