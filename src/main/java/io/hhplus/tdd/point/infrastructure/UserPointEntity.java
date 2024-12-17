package io.hhplus.tdd.point.infrastructure;


public record UserPointEntity(
        long id,
        long point,
        long updateMillis
) {
    public static UserPointEntity empty(long id) {
        return new UserPointEntity(id, 0, System.currentTimeMillis());
    }

}
