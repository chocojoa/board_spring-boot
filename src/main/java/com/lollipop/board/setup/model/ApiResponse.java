package com.lollipop.board.setup.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiResponse<T> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    private String timestamp;

    @Setter
    private Long processingTimeMs;

    public ApiResponse(T data) {
        this.data = data;
        this.timestamp = LocalDateTime.now().toString();
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(data);
    }

}
