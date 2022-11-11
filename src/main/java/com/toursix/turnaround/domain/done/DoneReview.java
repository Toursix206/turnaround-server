package com.toursix.turnaround.domain.done;

import com.toursix.turnaround.domain.activity.Activity;
import com.toursix.turnaround.domain.common.AuditingTimeEntity;
import com.toursix.turnaround.domain.common.Status;
import com.toursix.turnaround.domain.todo.TodoStage;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
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

    public static DoneReview newInstance(Onboarding onboarding, Activity activity, Done done) {
        return DoneReview.builder()
                .onboarding(onboarding)
                .activity(activity)
                .done(done)
                .rating(DoneReviewRating.of(0))
                .isWritten(false)
                .status(Status.ACTIVE)
                .build();
    }

    public void delete() {
        this.status = Status.DELETED;
    }

    public void update(int score, String content) {
        this.rating.setScore(score);
        this.content = content;
        this.isWritten = true;
    }

    public boolean checkTodoStage() {
        return this.getDone().getTodo().getStage().equals(TodoStage.SUCCESS) ||
                this.getDone().getTodo().getStage().equals(TodoStage.SUCCESS_REWARD);
    }
}
