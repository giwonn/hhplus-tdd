package io.hhplus.tdd.point.domain;

import io.hhplus.tdd.point.TransactionType;
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

}
