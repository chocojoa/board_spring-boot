package com.lollipop.board.common.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationParam {

    private String sortColumn;

    private String sortDirection;

    private int startIndex;

    private int pageSize;
}
