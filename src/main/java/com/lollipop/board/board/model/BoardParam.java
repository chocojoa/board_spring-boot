package com.lollipop.board.board.model;

import com.lollipop.board.common.model.DataTableDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

@Data
@Alias("boardParam")
@EqualsAndHashCode(callSuper = false)
public class BoardParam extends DataTableDTO {

    private int boardId;

    private String category;

    private String title;

    private String userName;

}
