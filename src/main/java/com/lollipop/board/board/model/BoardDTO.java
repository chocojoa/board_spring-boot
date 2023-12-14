package com.lollipop.board.board.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@ToString
@Alias("boardDTO")
public class BoardDTO {

    private int rowNumber;

    private int boardId;

    private String title;

    private String content;

    private String category;

    private String fixed;

    private String fixedAt;

    private String createdDate;

    private int userId;

    private String userName;

    private int commentCount;
}
