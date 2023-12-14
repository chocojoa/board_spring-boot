package com.lollipop.board.common.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DataTableColumnDTO {

    private String data;

    private String name;

    private boolean searchable;

    private boolean orderable;

    private DataTableSearchDTO search;

}
