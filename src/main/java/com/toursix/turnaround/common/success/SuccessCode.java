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

    // 홈
    GET_HOME_MAIN_INFO_SUCCESS(SuccessStatusCode.OK, "홈 메인 정보 조회 성공입니다."),

    // 방타버스
    GET_SPACE_MAIN_INFO_SUCCESS(SuccessStatusCode.OK, "방타버스 메인 정보 조회 성공입니다."),

    // 활동
    GET_ACTIVITIES_INFO_SUCCESS(SuccessStatusCode.OK, "활동 리스트 조회 성공입니다."),
    GET_ACTIVITY_GUIDE_INFO_SUCCESS(SuccessStatusCode.OK, "활동별 가이드 조회 성공입니다."),

    // 예약 활동
    GET_TODO_MAIN_INFO_SUCCESS(SuccessStatusCode.OK, "활동 일정 리스트 조회 성공입니다."),
    GET_TODO_INFO_SUCCESS(SuccessStatusCode.OK, "활동 일정 세부 내용 조회 성공입니다."),
    GET_TODO_DONE_REVIEW_SUCCESS(SuccessStatusCode.OK, "해당 활동의 리뷰 조회 성공입니다."),

    // 인테리어
    UPDATE_INTERIOR_CLEAN_SCORE_SUCCESS(SuccessStatusCode.OK, "인테리어 청소 성공입니다."),

    // 마이페이지
    GET_MY_PAGE_SETTING_INFO_SUCCESS(SuccessStatusCode.OK, "마이페이지 설정 조회 성공입니다."),
    GET_MY_PAGE_HOME_INFO_SUCCESS(SuccessStatusCode.OK, "마이페이지 홈 조회 성공입니다."),

    // 인증
    LOGIN_SUCCESS(SuccessStatusCode.OK, "로그인 성공입니다."),
    REISSUE_TOKEN_SUCCESS(SuccessStatusCode.OK, "토큰 갱신 성공입니다."),

    // 활동 리워드
    UPDATE_REWARD_SUCCESS(SuccessStatusCode.OK, "인증 완료한 활동에 대한 리워드 획득 성공입니다."),

    /**
     * 201 CREATED
     */
    CREATED_SUCCESS(SuccessStatusCode.CREATED, "생성 성공입니다."),
    CREATED_TODO_DONE_SUCCESS(SuccessStatusCode.CREATED, "활동 인증 성공입니다."),

    // 인증
    CREATED_USER_SUCCESS(SuccessStatusCode.CREATED, "회원가입 성공입니다."),

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
