package io.hhplus.tdd.point.controller.port.in;

import io.hhplus.tdd.point.domain.UserPoint;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UsePointRequest(
        @NotNull()
        @Min(value = 1)
        long amount
) {

    public UserPoint toDomain(long id) {
        return UserPoint.builder()
                .id(id)
                .point(amount)
                .build();
    }
}
