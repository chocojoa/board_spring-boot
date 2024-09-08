package com.lollipop.board.common.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class PaginationDTO<T> {

    private int totalCount;

    private List<T> dataList;

    public PaginationDTO(int totalCount, List<T> dataList) {
        this.totalCount = totalCount;
        this.dataList = dataList;
    }
}
