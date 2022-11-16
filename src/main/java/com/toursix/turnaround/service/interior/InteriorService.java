package com.toursix.turnaround.service.interior;

import com.toursix.turnaround.common.util.MathUtils;
import com.toursix.turnaround.domain.common.Constant;
import com.toursix.turnaround.domain.interior.Obtain;
import com.toursix.turnaround.domain.interior.repository.ObtainRepository;
import com.toursix.turnaround.domain.item.Item;
import com.toursix.turnaround.domain.space.Acquire;
import com.toursix.turnaround.domain.user.Onboarding;
import com.toursix.turnaround.domain.user.User;
import com.toursix.turnaround.domain.user.repository.UserRepository;
import com.toursix.turnaround.service.space.dto.response.SpaceMainInfoResponse;
import com.toursix.turnaround.service.user.UserServiceUtils;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class InteriorService {

    private final UserRepository userRepository;
    private final ObtainRepository obtainRepository;

    public SpaceMainInfoResponse updateCleanScore(Long obtainId, Long userId) {
        User user = UserServiceUtils.findUserById(userRepository, userId);
        Onboarding onboarding = user.getOnboarding();
        Item item = onboarding.getItem();
        InteriorValidateUtils.validateEnoughBroom(item);
        Obtain obtain = InteriorServiceUtils.findObtainById(obtainRepository, obtainId);
        InteriorValidateUtils.validateCleanableInterior(obtain);
        item.useBroom();
        obtain.cleanInterior();
        onboarding.updateObtain(obtain);
        onboarding.addExperience(Constant.USE_BROOM_EXPERIENCE);
        //TODO 공간 여러개인 경우 고려
        Acquire acquire = onboarding.getAcquires().get(0);
        List<Obtain> obtains = onboarding.getObtains();
        List<Obtain> equippedObtains = obtains.stream()
                .filter(Obtain::getIsEquipped)
                .collect(Collectors.toList());
        int total = equippedObtains.stream()
                .mapToInt(Obtain::getCleanScore)
                .sum();
        acquire.updateCleanScore(MathUtils.average(total, equippedObtains.size()));
        return SpaceMainInfoResponse.of(onboarding, acquire);
    }
}
