package com.toursix.turnaround.service.space;

import com.toursix.turnaround.domain.space.Acquire;
import com.toursix.turnaround.domain.user.Onboarding;
import com.toursix.turnaround.domain.user.User;
import com.toursix.turnaround.domain.user.repository.UserRepository;
import com.toursix.turnaround.service.space.dto.response.SpaceMainInfoResponse;
import com.toursix.turnaround.service.user.UserServiceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class SpaceRetrieveService {

    private final UserRepository userRepository;

    //TODO 토스트 메시지 정책 확정되면 반영
    public SpaceMainInfoResponse getSpaceMainInfo(Long userId) {
        User user = UserServiceUtils.findUserById(userRepository, userId);
        Onboarding onboarding = user.getOnboarding();
        //TODO 공간 여러개인 경우 고려
        Acquire acquire = onboarding.getAcquires().get(0);
        return SpaceMainInfoResponse.of(onboarding, acquire);
    }
}
