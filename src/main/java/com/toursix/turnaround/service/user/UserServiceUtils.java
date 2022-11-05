package com.toursix.turnaround.service.user;

import static com.toursix.turnaround.common.exception.ErrorCode.CONFLICT_USER_EXCEPTION;
import com.toursix.turnaround.common.exception.ConflictException;
import com.toursix.turnaround.common.exception.NotFoundException;
import com.toursix.turnaround.domain.user.User;
import com.toursix.turnaround.domain.user.UserSocialType;
import com.toursix.turnaround.domain.user.repository.UserRepository;
import com.toursix.turnaround.common.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserServiceUtils {

    static void validateNotExistsUser(UserRepository userRepository, String socialId, UserSocialType socialType) {
        if (userRepository.existsBySocialIdAndSocialType(socialId, socialType)) {
            throw new ConflictException(String.format("이미 존재하는 유저 (%s - %s) 입니다", socialId, socialType),
                    CONFLICT_USER_EXCEPTION);
        }
    }

    public static User findUserBySocialIdAndSocialType(UserRepository userRepository, String socialId, UserSocialType socialType) {
        return userRepository.findUserBySocialIdAndSocialType(socialId, socialType);
    }

    public static User findUserById(UserRepository userRepository, Long userId) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new NotFoundException(String.format("존재하지 않는 유저 (%s) 입니다", userId), NOT_FOUND_USER_EXCEPTION);
        }
        return user;
    }
}
