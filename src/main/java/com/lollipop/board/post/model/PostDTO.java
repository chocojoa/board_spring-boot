package com.lollipop.board.post.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@ToString
@Alias("postDTO")
public class PostDTO {

    private int postId;

    private int rowNumber;

    private String title;

    private String content;

    private String categoryId;

    private String fixed;

    private String fixedAt;

    private String createdDate;

    private int userId;

    private String author;

    private int viewCount;

    private int commentCount;
}
