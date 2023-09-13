package com.lollipop.board.common.model;

import lombok.Data;

import java.util.List;

@Data
public class DataTableDTO {

    private int draw;

    private int start;

    private int length;

    private DataTableSearchDTO search;

    private List<DataTableOrderDTO> order;

    private List<DataTableColumnDTO> columns;

}
