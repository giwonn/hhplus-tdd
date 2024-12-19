package io.hhplus.tdd.point.controller.port.out;

import io.hhplus.tdd.point.domain.UserPoint;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserPointResponse {

    private final long id;

    private final long point;

    public static UserPointResponse from(UserPoint userPoint) {
        return new UserPointResponse(
                userPoint.getId(),
                userPoint.getPoint()
        );
    }
}
