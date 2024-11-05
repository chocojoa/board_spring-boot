package com.lollipop.board.common.advice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ErrorField {

    private String field;

    private String message;

    private String invalidValue;

}
