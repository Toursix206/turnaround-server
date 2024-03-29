package com.toursix.turnaround.domain.user;

import com.toursix.turnaround.domain.common.AuditingTimeEntity;
import com.toursix.turnaround.domain.common.Status;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class Setting extends AuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private Boolean agreeAllPushNotification;

    @Column(nullable = false, length = 30)
    private Boolean agreeBenefitAndEvent;

    @Column(nullable = false, length = 30)
    private Boolean agreeActivityNotification;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private Status status;

    public static Setting newInstance() {
        return Setting.builder()
                .agreeAllPushNotification(true)
                .agreeBenefitAndEvent(true)
                .agreeActivityNotification(true)
                .status(Status.ACTIVE)
                .build();
    }

    public void setAllPushNotification(boolean agreeAllPushNotification) {
        this.agreeAllPushNotification = agreeAllPushNotification;
    }

    public void delete() {
        this.status = Status.DELETED;
    }
}
