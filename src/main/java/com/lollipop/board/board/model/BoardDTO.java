package com.lollipop.board.board.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
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
