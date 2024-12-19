package io.hhplus.tdd.point.controller.port.out;

import io.hhplus.tdd.point.domain.PointHistory;
import io.hhplus.tdd.point.domain.TransactionType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PointHistoryResponse {

    private final long id;
    private final long userId;
    private final long amount;
    private final TransactionType type;

    public static PointHistoryResponse from(PointHistory pointHistory) {
        return new PointHistoryResponse(
                pointHistory.getId(),
                pointHistory.getUserId(),
                pointHistory.getAmount(),
                pointHistory.getType()
        );
    }

}
