package com.lollipop.board.board.service;

import com.lollipop.board.board.mapper.BoardMapper;
import com.lollipop.board.board.model.BoardDTO;
import com.lollipop.board.board.model.BoardParam;
import com.lollipop.board.board.model.CommentDTO;
import com.lollipop.board.common.model.WrapperDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;

    public WrapperDTO retrieveBoardList(BoardParam boardParam){
        int totalCount = boardMapper.selectBoardCount(boardParam);
        List<BoardDTO> dataList = boardMapper.selectBoardList(boardParam);
        return new WrapperDTO(boardParam.getDraw(), totalCount, dataList);
    }

    /**
     * 게시판 정보 조회
     * @param boardParam
     * @return
     */
    public BoardDTO retrieveBoard(BoardParam boardParam) {
        return boardMapper.selectBoard(boardParam);
    }

    /**
     * 게시판 정보 저장
     * @param boardDTO
     * @return
     */
    public Map<String, Object> createBoard(BoardDTO boardDTO) {
        boardMapper.insertBoard(boardDTO);
        return new HashMap<>(Map.of("boardId", boardDTO.getBoardId()));
    }

    /**
     * 게시판 정보 수정
     * @param boardDTO
     * @return
     */
    public int modifyBoard(BoardDTO boardDTO) {
        return boardMapper.updateBoard(boardDTO);
    }

    /**
     * 댓글 목록 조회
     * @param boardParam
     * @return
     */
    public List<CommentDTO> retrieveCommentList(BoardParam boardParam) {
        return boardMapper.selectCommentList(boardParam);
    }

    /**
     * 댓글 정보 저장
     * @param commentDTO
     * @return
     */
    public int createComment(CommentDTO commentDTO) {
        return boardMapper.insertComment(commentDTO);
    }
    
}
