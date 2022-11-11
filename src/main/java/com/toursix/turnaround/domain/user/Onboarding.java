package com.toursix.turnaround.domain.user;

import com.toursix.turnaround.domain.activity.Scrap;
import com.toursix.turnaround.domain.common.AuditingTimeEntity;
import com.toursix.turnaround.domain.common.Status;
import com.toursix.turnaround.domain.done.DoneReview;
import com.toursix.turnaround.domain.interior.Obtain;
import com.toursix.turnaround.domain.item.Item;
import com.toursix.turnaround.domain.space.Acquire;
import com.toursix.turnaround.domain.todo.Todo;
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
public class Onboarding extends AuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private OnboardingProfileType profileType;

    @Column(unique = true, nullable = false, length = 30)
    private String nickname;

    @Column(length = 30)
    private String name;

    @Column(length = 100)
    private String phoneNumber;

    @Column(length = 300)
    private String address;

    @Column(nullable = false)
    private Integer level;

    @Column(nullable = false)
    private Integer experience;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @OneToMany(mappedBy = "onboarding", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Acquire> acquires = new ArrayList<>();

    @OneToMany(mappedBy = "onboarding", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Obtain> obtains = new ArrayList<>();

    @OneToMany(mappedBy = "onboarding", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Todo> todos = new ArrayList<>();

    @OneToMany(mappedBy = "onboarding", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<DoneReview> doneReviews = new ArrayList<>();

    @OneToMany(mappedBy = "onboarding", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Scrap> scraps = new ArrayList<>();

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private Status status;

    public static Onboarding newInstance(User user, OnboardingProfileType profileType, String nickname, Item item) {
        return Onboarding.builder()
                .user(user)
                .profileType(profileType)
                .nickname(nickname)
                .level(1)
                .experience(0)
                .item(item)
                .status(Status.ACTIVE)
                .build();
    }

    public void addAcquire(Acquire acquire) {
        this.acquires.add(acquire);
    }

    public void addObtain(Obtain obtain) {
        this.obtains.add(obtain);
    }    
    
    public void addTodo(Todo todo) {
        this.todos.add(todo);
    }

    public void updateTodo(Todo todo) {
        this.todos.set(todos.indexOf(todo), todo);
    }

    public void deleteTodo(Todo todo) {
        this.todos.remove(todo);
    }
    
    public void addDoneReview(DoneReview doneReview) {
        this.doneReviews.add(doneReview);
    }
}
