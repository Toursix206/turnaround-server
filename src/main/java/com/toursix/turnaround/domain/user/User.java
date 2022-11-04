package com.toursix.turnaround.domain.user;

import com.toursix.turnaround.domain.common.AuditingTimeEntity;
import com.toursix.turnaround.domain.common.Status;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends AuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Embedded
    private SocialInfo socialInfo;

    @Column(unique = true, length = 300)
    private String fcmToken;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Onboarding onboarding;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "setting_id", nullable = false)
    private Setting setting;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "point_id", nullable = false)
    private Point point;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private Status status;

    private User(String socialId, UserSocialType socialType) {
        this.socialInfo = SocialInfo.of(socialId, socialType);
        this.status = Status.ACTIVE;
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
