package com.toursix.turnaround.common.exception;

import static com.toursix.turnaround.common.exception.ErrorStatusCode.BAD_GATEWAY;
import static com.toursix.turnaround.common.exception.ErrorStatusCode.BAD_REQUEST;
import static com.toursix.turnaround.common.exception.ErrorStatusCode.CONFLICT;
import static com.toursix.turnaround.common.exception.ErrorStatusCode.FORBIDDEN;
import static com.toursix.turnaround.common.exception.ErrorStatusCode.INTERNAL_SERVER;
import static com.toursix.turnaround.common.exception.ErrorStatusCode.METHOD_NOT_ALLOWED;
import static com.toursix.turnaround.common.exception.ErrorStatusCode.NOT_ACCEPTABLE;
import static com.toursix.turnaround.common.exception.ErrorStatusCode.NOT_FOUND;
import static com.toursix.turnaround.common.exception.ErrorStatusCode.SERVICE_UNAVAILABLE;
import static com.toursix.turnaround.common.exception.ErrorStatusCode.UNAUTHORIZED;
import static com.toursix.turnaround.common.exception.ErrorStatusCode.UNSUPPORTED_MEDIA_TYPE;
import static com.toursix.turnaround.common.exception.ErrorStatusCode.UPGRADE_REQUIRED;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {

    /**
     * 400 Bad Request
     */
    VALIDATION_EXCEPTION(BAD_REQUEST, "잘못된 요청입니다."),
    VALIDATION_ENUM_VALUE_EXCEPTION(BAD_REQUEST, "잘못된 Enum 값 입니다."),
    VALIDATION_REQUEST_MISSING_EXCEPTION(BAD_REQUEST, "필수적인 요청 값이 입력되지 않았습니다."),
    VALIDATION_WRONG_TYPE_EXCEPTION(BAD_REQUEST, "잘못된 타입이 입력되었습니다."),
    VALIDATION_SOCIAL_TYPE_EXCEPTION(BAD_REQUEST, "잘못된 소셜 프로바이더 입니다."),
    VALIDATION_SORT_TYPE_EXCEPTION(BAD_REQUEST, "허용하지 않는 정렬기준을 입력했습니다."),
    VALIDATION_RATING_RANGE_EXCEPTION(BAD_REQUEST, "허용하지 않는 평점 범위를 입력하였습니다. (0 ~ 5)"),
    VALIDATION_OS_EXCEPTION(BAD_REQUEST, "잘못된 OS 타입 요청입니다."),
    VALIDATION_VERSION_EXCEPTION(BAD_REQUEST, "잘못된 버전 형식입니다."),

    /**
     * 401 UnAuthorized
     */
    UNAUTHORIZED_EXCEPTION(UNAUTHORIZED, "토큰이 만료되었습니다. 다시 로그인 해주세요."),
    UNAUTHORIZED_INVALID_SOCIAL_TOKEN_EXCEPTION(UNAUTHORIZED, "유효하지 않은 소셜 토큰입니다."),

    /**
     * 403 Forbidden
     */
    FORBIDDEN_EXCEPTION(FORBIDDEN, "허용하지 않는 요청입니다."),
    FORBIDDEN_FILE_TYPE_EXCEPTION(BAD_REQUEST, "허용되지 않은 파일 형식입니다."),
    FORBIDDEN_FILE_NAME_EXCEPTION(BAD_REQUEST, "허용되지 않은 파일 이름입니다."),

    /**
     * 404 Not Found
     */
    NOT_FOUND_EXCEPTION(NOT_FOUND, "존재하지 않습니다."),
    NOT_FOUND_OS_EXCEPTION(NOT_FOUND, "배포되지 않은 OS 입니다."),
    NOT_FOUND_USER_EXCEPTION(NOT_FOUND, "탈퇴했거나 존재하지 않는 유저입니다."),
    NOT_FOUND_REFRESH_TOKEN_EXCEPTION(NOT_FOUND, "만료된 리프레시 토큰입니다."),
    NOT_FOUND_ONBOARDING_EXCEPTION(NOT_FOUND, "유저의 온보딩 정보가 존재하지 않습니다."),
    NOT_FOUND_SPACE_CATEGORY_EXCEPTION(NOT_FOUND, "존재하지 않는 공간 카테고리입니다."),
    NOT_FOUND_SPACE_EXCEPTION(NOT_FOUND, "존재하지 않는 공간입니다."),
    NOT_FOUND_INTERIOR_EXCEPTION(NOT_FOUND, "존재하지 않는 인테리어입니다."),
    NOT_FOUND_ACTIVITY_EXCEPTION(NOT_FOUND, "존재하지 않는 활동입니다."),

    /**
     * 405 Method Not Allowed
     */
    METHOD_NOT_ALLOWED_EXCEPTION(METHOD_NOT_ALLOWED, "지원하지 않는 메소드 입니다."),

    /**
     * 406 Not Acceptable
     */
    NOT_ACCEPTABLE_EXCEPTION(NOT_ACCEPTABLE, "Not Acceptable"),

    /**
     * 409 Conflict
     */
    CONFLICT_EXCEPTION(CONFLICT, "이미 존재합니다."),
    CONFLICT_USER_EXCEPTION(CONFLICT, "이미 해당 계정으로 회원가입하셨습니다.\n로그인 해주세요."),
    CONFLICT_LOGIN_EXCEPTION(CONFLICT, "이미 로그인 중인 유저입니다."),
    CONFLICT_NICKNAME_EXCEPTION(CONFLICT, "이미 존재하는 닉네임입니다."),

    /**
     * 415 Unsupported Media Type
     */
    UNSUPPORTED_MEDIA_TYPE_EXCEPTION(UNSUPPORTED_MEDIA_TYPE, "해당하는 미디어 타입을 지원하지 않습니다."),

    /**
     * 426 Upgrade Required
     */
    UPGRADE_REQUIRED_EXCEPTION(UPGRADE_REQUIRED, "최신 버전으로 업그레이드가 필요합니다."),

    /**
     * 500 Internal Server Exception
     */
    INTERNAL_SERVER_EXCEPTION(INTERNAL_SERVER, "예상치 못한 서버 에러가 발생하였습니다."),

    /**
     * 502 Bad Gateway
     */
    BAD_GATEWAY_EXCEPTION(BAD_GATEWAY, "일시적인 에러가 발생하였습니다.\n잠시 후 다시 시도해주세요!"),

    /**
     * 503 Service UnAvailable
     */
    SERVICE_UNAVAILABLE_EXCEPTION(SERVICE_UNAVAILABLE, "현재 점검 중입니다.\n잠시 후 다시 시도해주세요!"),
    ;

    private final ErrorStatusCode statusCode;
    private final String message;

    public int getStatus() {
        return statusCode.getStatus();
    }
}
