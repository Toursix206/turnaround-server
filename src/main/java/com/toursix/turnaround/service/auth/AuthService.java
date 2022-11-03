package com.toursix.turnaround.service.auth;

import com.toursix.turnaround.service.auth.dto.request.LoginDto;
import com.toursix.turnaround.service.auth.dto.request.SignUpDto;

public interface AuthService {

    Long signUp(SignUpDto request);

    Long login(LoginDto request);

    Long forceLogin(LoginDto request);
}
