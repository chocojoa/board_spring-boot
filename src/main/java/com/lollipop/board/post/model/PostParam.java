package com.lollipop.board.post.model;

import com.lollipop.board.common.model.PaginationParam;
import lombok.*;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@ToString
@Alias("postParam")
public class PostParam extends PaginationParam {

    private Integer postId;

    private String categoryId;

    private String title;

    private String author;

    private String startCreatedDate;

    private String endCreatedDate;

}
