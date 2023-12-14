package com.lollipop.board.board.controller;

import com.lollipop.board.board.model.BoardDTO;
import com.lollipop.board.board.model.BoardParam;
import com.lollipop.board.board.model.CommentDTO;
import com.lollipop.board.board.service.BoardService;
import com.lollipop.board.common.model.WrapperDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    /**
     * 게시판 정보 목록 조회
     * @param boardParam
     * @return
     */
    @PostMapping("/boards")
    public WrapperDTO retrieveBoardList(@RequestBody BoardParam boardParam){
        return boardService.retrieveBoardList(boardParam);
    }

    /**
     * 게시판 정보 조회
     * @param boardParam
     * @return
     */
    @GetMapping("/board")
    public BoardDTO retrieveBoard(BoardParam boardParam){
        return boardService.retrieveBoard(boardParam);
    }

    /**
     * 게시판 정보 생성
     * @param boardDTO
     */
    @PostMapping("/board")
    public ResponseEntity<Map<String, Object>> createBoard(@RequestBody BoardDTO boardDTO){
        Map<String, Object> result = boardService.createBoard(boardDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * 게시판 정보 수정
     * @param boardDTO
     */
    @PutMapping("/board")
    public ResponseEntity<String> modifyBoard(@RequestBody BoardDTO boardDTO){
        boardService.modifyBoard(boardDTO);
        return ResponseEntity.ok().body(null);
    }

    /**
     * 댓글 목록 조회
     * @param boardParam
     */
    @GetMapping("/comments")
    public List<CommentDTO> retrieveCommentList(BoardParam boardParam){
        return boardService.retrieveCommentList(boardParam);
    }

    /**
     * 게시판 정보 생성
     * @param commentDTO
     */
    @PostMapping("/comment")
    public ResponseEntity<String> createComment(@RequestBody CommentDTO commentDTO){
        boardService.createComment(commentDTO);
        return ResponseEntity.ok().body(null);
    }

}
