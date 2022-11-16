package com.toursix.turnaround.domain.interior;

import com.toursix.turnaround.domain.common.AuditingTimeEntity;
import com.toursix.turnaround.domain.common.Status;
import com.toursix.turnaround.domain.user.Onboarding;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
public class Obtain extends AuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "onboarding_id", nullable = false)
    private Onboarding onboarding;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interior_id", nullable = false)
    private Interior interior;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private CleanLevel cleanLevel;

    @Column(nullable = false)
    private Integer cleanScore;

    @Column(nullable = false)
    private Boolean isEquipped;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private Status status;

    public static Obtain newInstance(Onboarding onboarding, Interior interior) {
        return Obtain.builder()
                .onboarding(onboarding)
                .interior(interior)
                .cleanLevel(CleanLevel.CLEAN)
                .cleanScore(100)
                .isEquipped(false)
                .status(Status.ACTIVE)
                .build();
    }

    public void equip() {
        this.isEquipped = true;
    }

    public void unequip() {
        this.isEquipped = false;
    }

    public void cleanInterior() {
        this.cleanLevel = this.cleanLevel.increase();
        this.cleanScore = this.cleanLevel.getMax();
    }
}
