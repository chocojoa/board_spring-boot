package com.lollipop.board.jwt.exception;

public class JwtExceptionUtil {

    public static String getErrorMessage(String errorCode) {
        return switch (errorCode) {
            case "INVALID_JWT_SIGNATURE" -> JwtErrorCode.INVALID_JWT_SIGNATURE.getErrorMsg();
            case "INVALID_JWT_TOKEN" -> JwtErrorCode.INVALID_JWT_TOKEN.getErrorMsg();
            case "JWT_TOKEN_EXPIRED" -> JwtErrorCode.JWT_TOKEN_EXPIRED.getErrorMsg();
            case "JWT_TOKEN_IS_UNSUPPORTED" -> JwtErrorCode.JWT_TOKEN_IS_UNSUPPORTED.getErrorMsg();
            case "JWT_CLAIMS_STRING_EMPTY" -> JwtErrorCode.JWT_CLAIMS_STRING_EMPTY.getErrorMsg();
            default -> "";
        };
    }

}
