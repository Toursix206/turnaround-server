package com.toursix.turnaround.domain.done;

import com.toursix.turnaround.domain.activity.Activity;
import com.toursix.turnaround.domain.common.AuditingTimeEntity;
import com.toursix.turnaround.domain.common.Status;
import com.toursix.turnaround.domain.user.Onboarding;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DoneReview extends AuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "onboarding_id", nullable = false)
    private Onboarding onboarding;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "activity_id", nullable = false)
    private Activity activity;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "done_id", nullable = false)
    private Done done;

    @Embedded
    private DoneReviewRating rating;

    @Column(length = 300)
    private String content;

    @Column(nullable = false)
    private Boolean isWritten;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private Status status;
}
