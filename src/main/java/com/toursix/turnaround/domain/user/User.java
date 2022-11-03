package com.toursix.turnaround.domain.user;

import com.toursix.turnaround.domain.common.AuditingTimeEntity;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "users")
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends AuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private SocialInfo socialInfo;

    @Column(unique = true, length = 300)
    private String fcmToken;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private User(String socialId, UserSocialType socialType) {
        this.socialInfo = SocialInfo.of(socialId, socialType);
        this.status = UserStatus.ACTIVE;
    }

    public static User newInstance(String socialId, UserSocialType socialType) {
        return new User(socialId, socialType);
    }

    public void updateFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public void resetFcmToken() {
        this.fcmToken = null;
    }
}
