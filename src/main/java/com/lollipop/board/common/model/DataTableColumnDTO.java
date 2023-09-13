package com.lollipop.board.common.model;

import lombok.Data;

@Data
public class DataTableColumnDTO {

    private String data;

    private String name;

    private boolean searchable;

    private boolean orderable;

    private DataTableSearchDTO search;

}
