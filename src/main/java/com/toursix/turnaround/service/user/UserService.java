package com.toursix.turnaround.service.user;

import com.toursix.turnaround.common.util.JwtUtils;
import com.toursix.turnaround.domain.interior.InteriorType;
import com.toursix.turnaround.domain.interior.Obtain;
import com.toursix.turnaround.domain.interior.repository.InteriorRepository;
import com.toursix.turnaround.domain.interior.repository.ObtainRepository;
import com.toursix.turnaround.domain.item.Item;
import com.toursix.turnaround.domain.item.repository.ItemRepository;
import com.toursix.turnaround.domain.space.Acquire;
import com.toursix.turnaround.domain.space.SpaceCategoryType;
import com.toursix.turnaround.domain.space.repository.AcquireRepository;
import com.toursix.turnaround.domain.space.repository.SpaceCategoryRepository;
import com.toursix.turnaround.domain.space.repository.SpaceRepository;
import com.toursix.turnaround.domain.user.Onboarding;
import com.toursix.turnaround.domain.user.Point;
import com.toursix.turnaround.domain.user.Setting;
import com.toursix.turnaround.domain.user.User;
import com.toursix.turnaround.domain.user.repository.OnbordingRepository;
import com.toursix.turnaround.domain.user.repository.PointRepository;
import com.toursix.turnaround.domain.user.repository.SettingRepository;
import com.toursix.turnaround.domain.user.repository.UserRepository;
import com.toursix.turnaround.service.interior.InteriorServiceUtils;
import com.toursix.turnaround.service.space.SpaceServiceUtils;
import com.toursix.turnaround.service.user.dto.request.CreateUserRequestDto;
import com.toursix.turnaround.service.user.dto.request.NicknameValidateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final SettingRepository settingRepository;
    private final PointRepository pointRepository;
    private final OnbordingRepository onbordingRepository;
    private final ItemRepository itemRepository;
    private final AcquireRepository acquireRepository;
    private final SpaceRepository spaceRepository;
    private final SpaceCategoryRepository spaceCategoryRepository;
    private final ObtainRepository obtainRepository;
    private final InteriorRepository interiorRepository;

    private final JwtUtils jwtProvider;

    public Long registerUser(CreateUserRequestDto request) {
        UserServiceUtils.validateNotExistsUser(userRepository, request.getSocialId(),
                request.getSocialType());
        UserServiceUtils.validateNickname(onbordingRepository, request.getNickname());
        User conflictFcmTokenUser = userRepository.findUserByFcmToken(request.getFcmToken());
        if (conflictFcmTokenUser != null) {
            jwtProvider.expireRefreshToken(conflictFcmTokenUser.getId());
            conflictFcmTokenUser.resetFcmToken();
        }
        User user = userRepository.save(
                User.newInstance(request.getSocialId(), request.getSocialType(),
                        settingRepository.save(Setting.newInstance()),
                        pointRepository.save(Point.newInstance())));
        Onboarding onboarding = onbordingRepository.save(
                Onboarding.newInstance(user, request.getProfileType(), request.getNickname(),
                        itemRepository.save(Item.newInstance())));
        Acquire acquire = acquireRepository.save(
                Acquire.newInstance(onboarding, SpaceServiceUtils.findSpaceBySpaceCategory(spaceRepository,
                        SpaceServiceUtils.findSpaceCategoryByName(spaceCategoryRepository,
                                SpaceCategoryType.SMALL_ROOM.getKey())
                )));
        onboarding.addAcquire(acquire);
        obtainBasicInteriors(onboarding);
        user.updateFcmToken(request.getFcmToken());
        user.setOnboarding(onboarding);
        return user.getId();
    }

    public void validateUniqueNickname(NicknameValidateRequestDto request) {
        UserServiceUtils.validateNickname(onbordingRepository, request.getNickname());
    }

    private void obtainBasicInteriors(Onboarding onboarding) {
        Obtain obtainBasicBed = obtainRepository.save(
                Obtain.newInstance(onboarding, InteriorServiceUtils.findInteriorByName(interiorRepository,
                        InteriorType.BASIC_BED.getKey())));
        Obtain obtainBasicTable = obtainRepository.save(
                Obtain.newInstance(onboarding, InteriorServiceUtils.findInteriorByName(interiorRepository,
                        InteriorType.BASIC_TABLE.getKey())));
        Obtain obtainBasicWall = obtainRepository.save(
                Obtain.newInstance(onboarding, InteriorServiceUtils.findInteriorByName(interiorRepository,
                        InteriorType.BASIC_WALL.getKey())));
        Obtain obtainBasicWindow = obtainRepository.save(
                Obtain.newInstance(onboarding, InteriorServiceUtils.findInteriorByName(interiorRepository,
                        InteriorType.BASIC_WINDOW.getKey())));
        obtainBasicBed.equip();
        obtainBasicTable.equip();
        obtainBasicWall.equip();
        obtainBasicWindow.equip();
        onboarding.addObtain(obtainBasicBed);
        onboarding.addObtain(obtainBasicTable);
        onboarding.addObtain(obtainBasicWall);
        onboarding.addObtain(obtainBasicWindow);
    }
}
