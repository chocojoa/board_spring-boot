package com.lollipop.board.board.model;

import com.lollipop.board.common.model.DataTableDTO;
import lombok.*;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@ToString
@Alias("boardParam")
public class BoardParam extends DataTableDTO {

    private int boardId;

    private String category;

    private String title;

    private String userName;

}
