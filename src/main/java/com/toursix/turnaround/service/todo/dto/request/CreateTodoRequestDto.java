package com.toursix.turnaround.service.todo.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.toursix.turnaround.domain.todo.PushStatus;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class CreateTodoRequestDto {

    @ApiModelProperty(value = "예약할 활동에 대한 id")
    private Long activityId;

    @ApiModelProperty(value = "알림 여부")
    private PushStatus pushStatus;

    @ApiModelProperty(value = "예약 시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime startAt;
}
