package com.lollipop.board.post.mapper;

import com.lollipop.board.post.model.PostDTO;
import com.lollipop.board.post.model.PostParam;
import com.lollipop.board.post.model.CommentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {

    /**
     * 게시글 전체 개수 조회
     * @param postParam 검색조건
     * @return 게시글 개수
     */
    int selectPostCount(PostParam postParam);

    /**
     * 게시글 목록 조회
     * @param postParam 검색조건
     * @return 게시글 목록
     */
    List<PostDTO> selectPostList(PostParam postParam);

    /**
     * 게시글 조회
     * @param postParam 검색조건
     * @return 게시글 정보
     */
    PostDTO selectPost(PostParam postParam);

    /**
     * 게시글 저장
     * @param postDTO 게시글 정보
     */
    void insertPost(PostDTO postDTO);

    /**
     * 게시글 수정
     * @param postDTO 게시글 정보
     */
    void updatePost(PostDTO postDTO);

    /**
     * 댓글 목록 조회
     * @param postParam 검색조건
     * @return 댓글 목록
     */
    List<CommentDTO> selectCommentList(PostParam postParam);

    /**
     * 댓글 저장
     * @param commentDTO 댓글 정보
     */
    void insertComment(CommentDTO commentDTO);

}
