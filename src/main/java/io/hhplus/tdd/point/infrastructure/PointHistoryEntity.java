package io.hhplus.tdd.point.infrastructure;

import io.hhplus.tdd.point.TransactionType;

public record PointHistoryEntity(
        long id,
        long userId,
        long amount,
        TransactionType type,
        long updateMillis
) {
}
