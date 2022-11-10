package com.toursix.turnaround.domain.todo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.toursix.turnaround.domain.activity.Activity;
import com.toursix.turnaround.domain.common.AuditingTimeEntity;
import com.toursix.turnaround.domain.common.Status;
import com.toursix.turnaround.domain.done.Done;
import com.toursix.turnaround.domain.user.Onboarding;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
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
public class Todo extends AuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "onboarding_id", nullable = false)
    private Onboarding onboarding;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id", nullable = false)
    private Activity activity;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private TodoStage stage;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Seoul")
    private LocalDateTime startAt;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Seoul")
    private LocalDateTime endAt;

    @OneToOne(mappedBy = "todo", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Done done;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private PushStatus pushStatus;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private Status status;

    public static Todo newInstance(Onboarding onboarding, Activity activity, LocalDateTime startAt,
            PushStatus pushStatus) {
        return Todo.builder()
                .onboarding(onboarding)
                .activity(activity)
                .stage(TodoStage.IN_PROGRESS)
                .startAt(startAt)
                .endAt(startAt.plusMinutes(activity.getDuration()))
                .pushStatus(pushStatus)
                .status(Status.ACTIVE)
                .build();
    }

    public void updateStartAt(LocalDateTime startAt) {
        this.startAt = startAt;
    }

    public void updatePushStatus(PushStatus pushStatus) {
        this.pushStatus = pushStatus;
    }

    public void delete() {
        this.status = Status.DELETED;
        if (this.getDone() != null) {
            this.getDone().delete();
        }
    }
}
