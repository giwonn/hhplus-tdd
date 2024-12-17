package io.hhplus.tdd.point.infrastructure;


import io.hhplus.tdd.point.domain.UserPoint;

public record UserPointEntity(
        long id,
        long point,
        long updateMillis
) {
    public static UserPointEntity empty(long id) {
        return new UserPointEntity(id, 0, System.currentTimeMillis());
    }

    public UserPoint toDomain() {
        return UserPoint.builder()
                .id(id)
                .point(point)
                .updateMillis(updateMillis)
                .build();
    }
}
