package io.hhplus.tdd.point.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class PointHistory {

    private long id;
    private long userId;
    private long amount;
    private TransactionType type;
    private long updateMillis;

    public static PointHistory fromUserPoint(UserPoint userPoint, TransactionType type) {
        return PointHistory.builder()
                .userId(userPoint.getId())
                .amount(userPoint.getPoint())
                .type(type)
                .build();
    }

}
