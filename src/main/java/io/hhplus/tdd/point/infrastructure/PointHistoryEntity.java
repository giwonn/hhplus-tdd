package io.hhplus.tdd.point.infrastructure;

import io.hhplus.tdd.point.domain.TransactionType;
import io.hhplus.tdd.point.domain.PointHistory;

public record PointHistoryEntity(
        long id,
        long userId,
        long amount,
        TransactionType type,
        long updateMillis
) {
    public PointHistory toDomain() {
        return PointHistory.builder()
                .id(id)
                .userId(userId)
                .amount(amount)
                .type(type)
                .updateMillis(updateMillis)
                .build();
    }
}
