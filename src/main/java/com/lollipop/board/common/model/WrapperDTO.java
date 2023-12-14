package com.lollipop.board.common.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
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
