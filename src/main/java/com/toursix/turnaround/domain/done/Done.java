package com.toursix.turnaround.domain.done;

import com.toursix.turnaround.domain.common.AuditingTimeEntity;
import com.toursix.turnaround.domain.common.Status;
import com.toursix.turnaround.domain.todo.Todo;
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
public class Done extends AuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id", nullable = false)
    private Todo todo;

    @Column(nullable = false, length = 300)
    private String imageUrl;

    @OneToOne(mappedBy = "done", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private DoneReview doneReview;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private Status status;

    public static Done newInstance(Todo todo, String imageUrl) {
        return Done.builder()
                .todo(todo)
                .imageUrl(imageUrl)
                .status(Status.ACTIVE)
                .build();
    }

    public void delete() {
        this.status = Status.DELETED;
        if (this.getDoneReview() != null) {
            this.getDoneReview().delete();
        }
    }
}
