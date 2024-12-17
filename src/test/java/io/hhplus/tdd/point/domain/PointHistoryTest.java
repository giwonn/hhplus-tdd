package io.hhplus.tdd.point.domain;

import io.hhplus.tdd.point.domain.exception.InvalidUserPointException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PointHistoryTest {

    @Test
    @DisplayName("포인트 히스토리를 조회할 수 있다.")
    void canChargeWhenBalanceIsLessThanMaxBalance() {
        // given
        UserPoint userPoint = UserPoint.builder().point(999_999L).build();

        // when
        userPoint.charge(1);

        // then
        assertThat(userPoint.getPoint()).isEqualTo(MAX_BALANCE);
    }

    @DisplayName("포인트 히스토리를 추가할 수 있다.")
    void failChargeWhenPointIsNonPositive(long invalidPoint) {
        // given
        UserPoint userPoint = UserPoint.builder().point(10L).build();

        // when & then
        assertThatThrownBy(() -> userPoint.charge(invalidPoint))
                .isInstanceOf(InvalidUserPointException.class);
    }

}
