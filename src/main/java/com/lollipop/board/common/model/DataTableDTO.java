package com.lollipop.board.common.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class DataTableDTO {

    private int draw;

    private int start;

    private int length;

    private DataTableSearchDTO search;

    private List<DataTableOrderDTO> order;

    private List<DataTableColumnDTO> columns;

}
