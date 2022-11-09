package com.toursix.turnaround.common.success;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessCode {

    /**
     * 200 OK
     */
    OK_SUCCESS(SuccessStatusCode.OK, "성공입니다."),
    GET_SPACE_MAIN_INFO_SUCCESS(SuccessStatusCode.OK, "방타버스 메인 정보 조회 성공입니다."),
    GET_ACTIVITY_INFO_SUCCESS(SuccessStatusCode.OK, "활동 리스트 조회 성공입니다."),

    // 인증
    LOGIN_SUCCESS(SuccessStatusCode.OK, "로그인 성공입니다."),
    REISSUE_TOKEN_SUCCESS(SuccessStatusCode.OK, "토큰 갱신 성공입니다."),

    /**
     * 201 CREATED
     */
    CREATED_SUCCESS(SuccessStatusCode.CREATED, "생성 성공입니다."),

    // 인증
    SIGNUP_SUCCESS(SuccessStatusCode.CREATED, "회원가입 성공입니다."),

    /**
     * 202 ACCEPTED
     */

    /**
     * 204 NO_CONTENT
     */
    ;

    private final SuccessStatusCode statusCode;
    private final String message;

    public int getStatus() {
        return statusCode.getStatus();
    }
}
