package com.lollipop.board.board.mapper;

import com.lollipop.board.board.model.BoardDTO;
import com.lollipop.board.board.model.BoardParam;
import com.lollipop.board.board.model.CommentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    /**
     * 게시판 목록 개수 조회
     * @param boardParam
     * @return
     */
    int selectBoardCount(BoardParam boardParam);

    /**
     * 게시판 목록 조회
     * @param boardParam
     * @return
     */
    List<BoardDTO> selectBoardList(BoardParam boardParam);

    /**
     * 게시판 정보 조회
     * @param boardParam
     * @return
     */
    BoardDTO selectBoard(BoardParam boardParam);

    /**
     * 게시판 정보 저장
     * @param boardDTO
     * @return
     */
    int insertBoard(BoardDTO boardDTO);

    /**
     * 게시판 정보 수정
     * @param boardDTO
     * @return
     */
    int updateBoard(BoardDTO boardDTO);

    /**
     * 댓글 목록 조회
     * @param boardParam
     * @return
     */
    List<CommentDTO> selectCommentList(BoardParam boardParam);

    /**
     * 댓글 정보 저장
     * @param commentDTO
     * @return
     */
    int insertComment(CommentDTO commentDTO);

}
