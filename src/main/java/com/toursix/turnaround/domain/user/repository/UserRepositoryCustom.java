package com.toursix.turnaround.domain.user.repository;

import com.toursix.turnaround.domain.user.User;
import com.toursix.turnaround.domain.user.UserSocialType;

public interface UserRepositoryCustom {

    boolean existsBySocialIdAndSocialType(String socialId, UserSocialType socialType);

    User findUserById(Long id);

    User findUserBySocialIdAndSocialType(String socialId, UserSocialType socialType);

    User findUserByFcmToken(String fcmToken);
}
