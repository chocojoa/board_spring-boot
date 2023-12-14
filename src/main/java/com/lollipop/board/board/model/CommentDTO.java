package com.lollipop.board.board.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@ToString
@Alias("commentDTO")
public class CommentDTO {

    private int commentId;

    private String content;

    private int parentCommentId;

    private int step;

    private int boardId;

    private String createdDate;

    private int userId;

    private String userName;

    private String email;
}
