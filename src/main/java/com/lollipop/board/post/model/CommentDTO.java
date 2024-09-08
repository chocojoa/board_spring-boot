package com.lollipop.board.post.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@ToString
@Alias("commentDTO")
public class CommentDTO {

    private String categoryId;

    private int postId;

    private int commentId;

    private String content;

    private int parentCommentId;

    private int step;

    private String createdDate;

    private int userId;

    private String userName;

    private String email;
}
