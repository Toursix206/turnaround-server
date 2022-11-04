package com.toursix.turnaround.domain.activity;

import com.toursix.turnaround.domain.common.AuditingTimeEntity;
import com.toursix.turnaround.domain.done.DoneReview;
import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Activity extends AuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private ActivityType type;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private ActivityCategory category;

    @Column(nullable = false, length = 300)
    private String imageUrl;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false)
    private Integer broom;

    @Column(nullable = false)
    private Integer point;

    @Column(nullable = false)
    private Integer duration;

    @Column(nullable = false, length = 100)
    private String supply;

    @Column(nullable = false)
    private Integer steps;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "activity_guide_id", nullable = false)
    private final List<ActivityGuide> guides = new ArrayList<>();

    @OneToMany(mappedBy = "activity", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<DoneReview> doneReviews = new ArrayList<>();

    //TODO 아이템 테이블 먼저 만들고 리워드 칼럼 리스트로 만들기 -> 이중 무작위로 하나 주는 방식으로 구현
}
