package com.lollipop.board.common.model;

import lombok.Data;

import java.util.List;

@Data
public class WrapperDTO {

    private int draw;

    private int recordsTotal;

    private int recordsFiltered;

    private List<?> data;

    public WrapperDTO(int draw, int totalCount, List<?> data) {
        this.draw = draw;
        this.recordsTotal = totalCount;
        this.recordsFiltered = totalCount;
        this.data = data;
    }
}
