package io.hhplus.tdd.point.domain;

import io.hhplus.tdd.point.domain.exception.ExceedUserPointLimitException;
import io.hhplus.tdd.point.domain.exception.InSufficientUserPointException;
import io.hhplus.tdd.point.domain.exception.InvalidUserPointException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class UserPointTest {

    private final long MAX_BALANCE = 1_000_000L;


    @Nested()
    class 포인트_충전 {

        @Test
        void 충전하여_잔고에_최대치까지_저장할_수_있다() {
            // given
            UserPoint userPoint = UserPoint.builder().point(999_999L).build();

            // when
            userPoint.charge(1);

            // then
            assertThat(userPoint.getPoint()).isEqualTo(MAX_BALANCE);
        }

        @ParameterizedTest
        @ValueSource(longs = {0, -1})
        void 양수가_아닌_값으로_충전을_시도하면_예외가_발생한다(long invalidPoint) {
            // given
            UserPoint userPoint = UserPoint.builder().point(10L).build();

            // when & then
            assertThatThrownBy(() -> userPoint.charge(invalidPoint))
                    .isInstanceOf(InvalidUserPointException.class);
        }

        @Test
        void 충전_후의_잔액이_최대치를_초과하면_예외가_발생한다() {
            // given
            UserPoint userPoint = UserPoint.builder().point(MAX_BALANCE).build();

            // when & then
            assertThatThrownBy(() -> userPoint.charge(1))
                    .isInstanceOf(ExceedUserPointLimitException.class);
        }
    }

    @Nested()
    class 포인트_사용 {

        @Test
        void 잔액을_포함하여_최대_100만_포인트까지_사용할_수_있다() {
            // given
            UserPoint userPoint = UserPoint.builder().point(MAX_BALANCE).build();

            // when
            userPoint.use(MAX_BALANCE);

            // then
            assertThat(userPoint.getPoint()).isZero();
        }

        @ParameterizedTest
        @ValueSource(longs = {0, -1})
        void 양수가_아닌_값으로_사용을_시도하면_예외가_발생한다(long invalidPoint) {
            // given
            UserPoint userPoint = UserPoint.builder().point(10L).build();

            // when & then
            assertThatThrownBy(() -> userPoint.charge(invalidPoint))
                    .isInstanceOf(InvalidUserPointException.class);
        }

        @Test
        void 잔액보다_많은_포인트_사용을_시도하면_예외가_발생한다() {
            // given
            UserPoint userPoint = UserPoint.builder().point(1).build();

            // when & then
            assertThatThrownBy(() -> userPoint.use(2))
                    .isInstanceOf(InSufficientUserPointException.class);
        }
    }

}
