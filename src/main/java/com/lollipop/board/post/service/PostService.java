package com.lollipop.board.post.service;

import com.lollipop.board.setup.redis.dao.RedisDAO;
import com.lollipop.board.post.mapper.PostMapper;
import com.lollipop.board.post.model.PostDTO;
import com.lollipop.board.post.model.PostParam;
import com.lollipop.board.post.model.CommentDTO;
import com.lollipop.board.common.model.PaginationDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostMapper postMapper;
    private final RedisDAO redisDAO;

    /**
     * 게시글 목록 조회
     *
     * @param postParam 검색조건
     * @return 게시글 목록
     */
    public PaginationDTO<PostDTO> retrievePostList(PostParam postParam) {
        int totalCount = postMapper.selectPostCount(postParam);
        List<PostDTO> dataList = postMapper.selectPostList(postParam);
        return new PaginationDTO<>(totalCount, dataList);
    }

    /**
     * 게시글 조회
     *
     * @param categoryId 카테고리 아이디
     * @param postId     게시글 아이디
     * @return 게시글 정보
     */
    public PostDTO retrievePost(String categoryId, Integer postId, Integer userId) {
        if(userId != null) {
            modifyPostViewCount(categoryId, postId, userId);
        }
        PostParam postParam = new PostParam();
        postParam.setCategoryId(categoryId);
        postParam.setPostId(postId);
        PostDTO postDTO = postMapper.selectPost(postParam);
        if (postDTO == null) {
            throw new NoSuchElementException("Post not found");
        }
        return postDTO;
    }

    /**
     * 게시글 조회수 수정
     *
     * @param categoryId 카테고리 아이디
     * @param postId     게시글 아이디
     * @param userId     사용자 아이디
     */
    private void modifyPostViewCount(String categoryId, Integer postId, Integer userId) {
        String redisPostViewKey = "postView_" + userId;
        String postView = redisDAO.getValues(redisPostViewKey);

        PostDTO postDTO = new PostDTO();
        postDTO.setCategoryId(categoryId);
        postDTO.setPostId(postId);

        if (postView == null) {
            redisDAO.setValues(redisPostViewKey, postId.toString(), Duration.ofDays(1));
            postMapper.updateViewCount(postDTO);
        } else {
            String[] views = postView.split(",");
            List<String> viewList = new ArrayList<>(Arrays.asList(views));

            if (!viewList.contains(postId.toString())) {
                viewList.add(postId.toString());
                String viewListStr = String.join(",", viewList);
                redisDAO.setValues(redisPostViewKey, viewListStr, Duration.ofDays(1));
                postMapper.updateViewCount(postDTO);
            }
        }
    }

    /**
     * 게시글 저장
     *
     * @param categoryId 카테고리 아이디
     * @param postDTO    게시글 정보
     * @return 저장된 게시글
     */
    public PostDTO createPost(String categoryId, PostDTO postDTO) {
        postDTO.setCategoryId(categoryId);
        postMapper.insertPost(postDTO);
        return postDTO;
    }

    /**
     * 게시글 수정
     *
     * @param categoryId 카테고리 아이디
     * @param postId     게시글 아이디
     * @param postDTO    게시글 정보
     * @return 수정된 게시글
     */
    public PostDTO modifyPost(String categoryId, int postId, PostDTO postDTO) {
        PostParam postParam = new PostParam();
        postParam.setCategoryId(categoryId);
        postParam.setPostId(postId);
        PostDTO retrievePost = postMapper.selectPost(postParam);
        if (retrievePost == null) {
            throw new NoSuchElementException("Post not found");
        }
        retrievePost.setTitle(postDTO.getTitle());
        retrievePost.setContent(postDTO.getContent());
        retrievePost.setUserId(postDTO.getUserId());
        postMapper.updatePost(retrievePost);
        return retrievePost;
    }

    /**
     * 댓글 목록 조회
     *
     * @param categoryId 카테고리 아이디
     * @param postId     게시글 아이디
     * @return 댓글 목록
     */
    public List<CommentDTO> retrieveCommentList(String categoryId, int postId) {
        PostParam postParam = new PostParam();
        postParam.setCategoryId(categoryId);
        postParam.setPostId(postId);
        return postMapper.selectCommentList(postParam);
    }

    /**
     * 댓글 정보 저장
     *
     * @param commentDTO 댓글 정보
     * @return 저장된 댓글 정보
     */
    public CommentDTO createComment(String categoryId, int postId, CommentDTO commentDTO) {
        commentDTO.setCategoryId(categoryId);
        commentDTO.setPostId(postId);
        postMapper.insertComment(commentDTO);
        return commentDTO;
    }

}
