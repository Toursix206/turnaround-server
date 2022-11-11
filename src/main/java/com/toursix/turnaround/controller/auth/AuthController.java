package com.toursix.turnaround.controller.auth;

import com.toursix.turnaround.common.dto.ErrorResponse;
import com.toursix.turnaround.common.dto.SuccessResponse;
import com.toursix.turnaround.common.success.SuccessCode;
import com.toursix.turnaround.config.interceptor.Auth;
import com.toursix.turnaround.config.resolver.UserId;
import com.toursix.turnaround.controller.auth.dto.request.LoginRequestDto;
import com.toursix.turnaround.controller.auth.dto.request.SignUpRequestDto;
import com.toursix.turnaround.controller.auth.dto.response.LoginResponse;
import com.toursix.turnaround.service.auth.AuthService;
import com.toursix.turnaround.service.auth.AuthServiceProvider;
import com.toursix.turnaround.service.auth.CommonAuthService;
import com.toursix.turnaround.service.auth.CreateTokenService;
import com.toursix.turnaround.service.auth.dto.request.TokenRequestDto;
import com.toursix.turnaround.service.auth.dto.response.RefreshResponse;
import com.toursix.turnaround.service.auth.dto.response.TokenResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "Auth")
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceProvider authServiceProvider;
    private final CreateTokenService createTokenService;
    private final CommonAuthService commonAuthService;

    @ApiOperation(
            value = "온보딩 페이지 - 회원가입을 요청합니다.",
            notes = "카카오 회원가입, 애플 회원가입을 요청합니다.\n" +
                    "socialType - KAKAO (카카오), APPLE (애플)"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "회원가입 성공입니다."),
            @ApiResponse(
                    code = 400,
                    message = "1. 유저의 socialType 를 입력해주세요. (socialType)\n"
                            + "2. access token 을 입력해주세요. (token)\n"
                            + "3. 유저의 fcm token 을 입력해주세요. (fcmToken)\n"
                            + "4. 유저의 profileType 을 입력해주세요. (profileType)\n"
                            + "5. 유저의 nickname 을 입력해주세요. (nickname)",
                    response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "유효하지 않은 토큰입니다.", response = ErrorResponse.class),
            @ApiResponse(
                    code = 404,
                    message = "1. 존재하지 않는 공간 카테고리입니다.\n"
                            + "2. 존재하지 않는 공간입니다.\n"
                            + "3. 존재하지 않는 인테리어입니다.",
                    response = ErrorResponse.class),
            @ApiResponse(
                    code = 409,
                    message = "1. 이미 해당 계정으로 회원가입하셨습니다.\n   로그인 해주세요.\n"
                            + "2. 이미 존재하는 닉네임입니다.",
                    response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생하였습니다.", response = ErrorResponse.class)
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/auth/signup")
    public ResponseEntity<SuccessResponse<LoginResponse>> signUp(
            @Valid @RequestBody SignUpRequestDto request) {
        AuthService authService = authServiceProvider.getAuthService(request.getSocialType());
        Long userId = authService.signUp(request.toServiceDto());
        TokenResponse tokenInfo = createTokenService.createTokenInfo(userId);
        return SuccessResponse.success(SuccessCode.CREATED_USER_SUCCESS,
                LoginResponse.of(userId, tokenInfo));
    }

    @ApiOperation(
            value = "로그인 페이지 - 로그인을 요청합니다.",
            notes = "카카오 로그인, 애플 로그인을 요청합니다.\n" +
                    "socialType - KAKAO (카카오), APPLE (애플)\n" +
                    "회원가입이 완료되지 않은 사용자일 경우 404 에러를 전달합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "로그인 성공입니다."),
            @ApiResponse(
                    code = 400,
                    message = "1. 유저의 socialType 를 입력해주세요. (socialType)\n"
                            + "2. access token 을 입력해주세요. (token)\n"
                            + "3. fcm token 을 입력해주세요. (fcmToken)",
                    response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "유효하지 않은 토큰입니다.", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "탈퇴했거나 존재하지 않는 유저입니다.", response = ErrorResponse.class),
            @ApiResponse(code = 409, message = "이미 로그인 중인 유저입니다.", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생하였습니다.", response = ErrorResponse.class)
    })
    @PostMapping("/auth/login")
    public ResponseEntity<SuccessResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequestDto request) {
        AuthService authService = authServiceProvider.getAuthService(request.getSocialType());
        Long userId = authService.login(request.toServiceDto());
        TokenResponse tokenInfo = createTokenService.createTokenInfo(userId);
        return SuccessResponse.success(SuccessCode.LOGIN_SUCCESS,
                LoginResponse.of(userId, tokenInfo));
    }

    @ApiOperation(
            value = "로그인 페이지 - 강제 로그인을 요청합니다.",
            notes = "카카오 로그인, 애플 로그인을 요청합니다.\n" +
                    "socialType - KAKAO (카카오), APPLE (애플)\n" +
                    "회원가입이 완료되지 않은 사용자일 경우 404 에러를 전달합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "로그인 성공입니다."),
            @ApiResponse(
                    code = 400,
                    message = "1. 유저의 socialType 를 입력해주세요. (socialType)\n"
                            + "2. access token 을 입력해주세요. (token)\n"
                            + "3. fcm token 을 입력해주세요. (fcmToken)",
                    response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "유효하지 않은 토큰입니다.", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "탈퇴했거나 존재하지 않는 유저입니다.", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생하였습니다.", response = ErrorResponse.class)
    })
    @PostMapping("/auth/login/force")
    public ResponseEntity<SuccessResponse<LoginResponse>> forceLogin(
            @Valid @RequestBody LoginRequestDto request) {
        AuthService authService = authServiceProvider.getAuthService(request.getSocialType());
        Long userId = authService.forceLogin(request.toServiceDto());
        TokenResponse tokenInfo = createTokenService.createTokenInfo(userId);
        return SuccessResponse.success(SuccessCode.LOGIN_SUCCESS,
                LoginResponse.of(userId, tokenInfo));
    }

    @ApiOperation(
            value = "JWT 인증 - Access Token 을 갱신합니다.",
            notes = "만료된 Access Token 을 Refresh Token 으로 갱신합니다.\n" +
                    "Refresh Token 이 유효하지 않거나 만료된 경우 갱신에 실패합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "토큰 갱신 성공입니다."),
            @ApiResponse(
                    code = 400,
                    message = "1. access token 을 입력해주세요. (accessToken)\n"
                            + "2. refresh token 을 입력해주세요. (refreshToken)",
                    response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "토큰이 만료되었습니다. 다시 로그인 해주세요.", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "탈퇴했거나 존재하지 않는 유저입니다.", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생하였습니다.", response = ErrorResponse.class)
    })
    @PostMapping("/auth/refresh")
    public ResponseEntity<SuccessResponse<RefreshResponse>> reissue(
            @Valid @RequestBody TokenRequestDto request) {
        return SuccessResponse.success(SuccessCode.REISSUE_TOKEN_SUCCESS,
                createTokenService.reissueToken(request));
    }

    @ApiOperation(
            value = "[인증] 마이 페이지(설정) - 로그아웃을 요청합니다.",
            notes = "로그아웃 성공시 로그인 페이지로 이동합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공입니다."),
            @ApiResponse(code = 401, message = "토큰이 만료되었습니다. 다시 로그인 해주세요.", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "탈퇴했거나 존재하지 않는 유저입니다.", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생하였습니다.", response = ErrorResponse.class)
    })
    @Auth
    @PostMapping("/auth/logout")
    public ResponseEntity<SuccessResponse<String>> logout(@ApiIgnore @UserId Long userId) {
        commonAuthService.logout(userId);
        return SuccessResponse.OK;
    }
}
