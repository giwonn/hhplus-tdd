package io.hhplus.tdd.point.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PointHistoryTest {

    @Test
    @DisplayName("포인트 히스토리를 추가할 수 있다.")
    void canChargeWhenBalanceIsLessThanMaxBalance() {
        // given
        UserPoint userPoint = UserPoint.builder().point(999_999L).build();

        // when
        userPoint.charge(1);

        // then
        assertThat(userPoint.getPoint()).isEqualTo(1_000_000L); // TODO: 최댓값이 감소하도록 변경되면 테스트 깨짐...
    }

}
