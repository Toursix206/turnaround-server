package com.toursix.turnaround.service.todo.dto.request;

import com.toursix.turnaround.domain.todo.PushStatus;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
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
public class UpdateTodoPushStateRequestDto {

    @NotNull(message = "{todo.pushState.notNull}")
    @ApiModelProperty(value = "알림 여부")
    private PushStatus pushStatus;
}
