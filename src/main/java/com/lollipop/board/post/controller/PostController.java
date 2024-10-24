package com.lollipop.board.post.controller;

import com.lollipop.board.post.model.PostDTO;
import com.lollipop.board.post.model.PostParam;
import com.lollipop.board.post.model.CommentDTO;
import com.lollipop.board.post.service.PostService;
import com.lollipop.board.common.model.ApiResponse;
import com.lollipop.board.common.model.PaginationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    /**
     * 게시글 목록 조회
     *
     * @param categoryId 카테고리 아이디
     * @param postParam  검색조건
     * @return 게시글 목록
     */
    @GetMapping("/{categoryId}/posts")
    public ResponseEntity<ApiResponse<PaginationDTO<PostDTO>>> retrievePostList(
            @PathVariable("categoryId") String categoryId,
            PostParam postParam) {
        postParam.setCategoryId(categoryId);
        PaginationDTO<PostDTO> postList = postService.retrievePostList(postParam);
        return ResponseEntity.ok().body(ApiResponse.success(postList));
    }

    /**
     * 게시글 정보 조회
     *
     * @param categoryId 카테고리 아이디
     * @param postId     게시글 아이디
     * @return 게시글 정보
     */
    @GetMapping("/{categoryId}/posts/{postId}")
    public ResponseEntity<ApiResponse<PostDTO>> retrievePost(
            @PathVariable("categoryId") String categoryId,
            @PathVariable("postId") int postId) {
        PostDTO post = postService.retrievePost(categoryId, postId);
        return ResponseEntity.ok().body(ApiResponse.success(post));
    }

    /**
     * 게시글 생성
     *
     * @param categoryId 카테고리 아이디
     * @param postDTO    게시글 정보
     * @return 생성된 게시글 정보
     */
    @PostMapping("/{categoryId}/posts")
    public ResponseEntity<ApiResponse<PostDTO>> createPost(
            @PathVariable("categoryId") String categoryId,
            @RequestBody PostDTO postDTO) {
        PostDTO savedPostDTO = postService.createPost(categoryId, postDTO);
        return ResponseEntity.ok().body(ApiResponse.success(savedPostDTO));
    }

    /**
     * 게시글 수정
     *
     * @param categoryId 카테고리 아이디
     * @param postId     게시글 아이디
     * @param postDTO    게시글 정보
     * @return 수정된 게시글 정보
     */
    @PutMapping("/{categoryId}/posts/{postId}")
    public ResponseEntity<ApiResponse<PostDTO>> modifyPost(
            @PathVariable("categoryId") String categoryId,
            @PathVariable("postId") int postId,
            @RequestBody PostDTO postDTO) {
        PostDTO modifiedPostDTO = postService.modifyPost(categoryId, postId, postDTO);
        return ResponseEntity.ok().body(ApiResponse.success(modifiedPostDTO));
    }

    /**
     * 댓글 목록 조회
     *
     * @param categoryId 카테고리 아이디
     * @param postId     게시글 아이디
     * @return 댓글 목록
     */
    @GetMapping("/{categoryId}/posts/{postId}/comments")
    public ResponseEntity<ApiResponse<List<CommentDTO>>> retrieveCommentList(
            @PathVariable("categoryId") String categoryId,
            @PathVariable("postId") int postId) {
        List<CommentDTO> commentList = postService.retrieveCommentList(categoryId, postId);
        return ResponseEntity.ok().body(ApiResponse.success(commentList));
    }

    /**
     * 댓글 작성
     *
     * @param categoryId 카테고리 아이디
     * @param postId     게시글 아이디
     * @param commentDTO 댓글 정보
     * @return 저장된 댓글 정보
     */
    @PostMapping("/{categoryId}/posts/{postId}/comments")
    public ResponseEntity<ApiResponse<CommentDTO>> createComment(
            @PathVariable("categoryId") String categoryId,
            @PathVariable("postId") int postId,
            @RequestBody CommentDTO commentDTO) {
        CommentDTO createdCommentDTO = postService.createComment(categoryId, postId, commentDTO);
        return ResponseEntity.ok().body(ApiResponse.success(createdCommentDTO));
    }
}