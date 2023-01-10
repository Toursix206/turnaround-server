package com.toursix.turnaround.service.user;


import com.toursix.turnaround.domain.deploy.Deploy;
import com.toursix.turnaround.domain.deploy.repository.DeployRepository;
import com.toursix.turnaround.domain.user.User;
import com.toursix.turnaround.domain.user.repository.UserRepository;
import com.toursix.turnaround.service.user.dto.response.MyPageHomeResponse;
import com.toursix.turnaround.service.user.dto.response.MyPageSettingResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserRetrieveService {

    private final UserRepository userRepository;
    private final DeployRepository deployRepository;

    public MyPageSettingResponse getMyPageSetting(Long userId) {
        User user = UserServiceUtils.findUserById(userRepository, userId);
        return MyPageSettingResponse.of(user.getSetting().getAgreeAllPushNotification());
    }

    public MyPageHomeResponse getMyPageHome(Long userId) {
        User user = UserServiceUtils.findUserById(userRepository, userId);
        List<Deploy> deploys = deployRepository.findAll();
        Map<String, String> deployByOs = deploys.stream()
                .collect(Collectors.toMap(Deploy::getOs, Deploy::getMarketUrl));
        return MyPageHomeResponse.of(user, deployByOs);
    }
}
