package com.lollipop.board.jwt.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JwtErrorCode {

    INVALID_JWT_SIGNATURE("INVALID_JWT_SIGNATURE", "토큰 서명이 잘못 되었습니다."),
    INVALID_JWT_TOKEN("INVALID_JWT_TOKEN", "유효하지 않은 토큰입니다."),
    JWT_TOKEN_EXPIRED("JWT_TOKEN_EXPIRED", "토큰이 만료 되었습니다."),
    JWT_TOKEN_IS_UNSUPPORTED("JWT_TOKEN_IS_UNSUPPORTED", "토큰을 지원하지 않습니다."),
    JWT_CLAIMS_STRING_EMPTY("JWT_CLAIMS_STRING_EMPTY", "토큰 클레임 문자열이 비어 있습니다.");

    private final String errorCode;
    private final String errorMsg;

}
