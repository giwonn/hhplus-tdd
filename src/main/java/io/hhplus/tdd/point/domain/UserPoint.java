package io.hhplus.tdd.point.domain;

import io.hhplus.tdd.point.domain.exception.InSufficientUserPointException;
import io.hhplus.tdd.point.domain.exception.ExceedUserPointLimitException;
import io.hhplus.tdd.point.domain.exception.InvalidUserPointException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class UserPoint {

    static final long MAX_BALANCE = 1_000_000L;

    private long id;
    private long point;
    private long updateMillis;

    public static UserPoint empty(long id) {
        return new UserPoint(id, 0, System.currentTimeMillis());
    }

    public void charge(long point) {
        this.validatePoint(point);
        if (this.point + point > MAX_BALANCE) {
            throw new ExceedUserPointLimitException();
        }
        this.point += point;
    }

    public void use(long point) {
        this.validatePoint(point);
        if (point > this.point) {
            throw new InSufficientUserPointException();
        }
        this.point -= point;
    }

    private void validatePoint(long point) {
        if (point <= 0 || point > MAX_BALANCE) {
            throw new InvalidUserPointException();
        }
    }

}
