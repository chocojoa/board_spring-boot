package com.lollipop.board.setup.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.FORBIDDEN)
public class MenuAccessDeniedException extends RuntimeException {

    public MenuAccessDeniedException(String message) {
        super(message);
    }
}
