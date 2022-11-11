package com.toursix.turnaround.service.user;


import com.toursix.turnaround.domain.user.User;
import com.toursix.turnaround.domain.user.repository.UserRepository;
import com.toursix.turnaround.service.user.dto.response.MyPageSettingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserRetrieveService {

    private final UserRepository userRepository;

    public MyPageSettingResponse getMyPageSetting(Long userId) {
        User user = UserServiceUtils.findUserById(userRepository, userId);
        return MyPageSettingResponse.of(user.getSetting().getAgreeActivityNotification());
    }

}
