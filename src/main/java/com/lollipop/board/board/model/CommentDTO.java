package com.lollipop.board.board.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
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
