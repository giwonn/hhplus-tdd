package io.hhplus.tdd.point.service;

import io.hhplus.tdd.point.domain.TransactionType;
import io.hhplus.tdd.point.domain.PointHistory;
import io.hhplus.tdd.point.domain.UserPoint;
import io.hhplus.tdd.point.service.port.PointHistoryRepository;
import io.hhplus.tdd.point.service.port.UserPointRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PointServiceTest {

    @Mock
    UserPointRepository userPointRepository;

    @Mock
    PointHistoryRepository pointHistoryRepository;

    @InjectMocks
    PointService pointService;

    @Test
    void userId로_포인트를_조회하면_현재_포인트를_반환한다() {

        // given
        long userId = 1L;
        UserPoint expected = UserPoint.builder()
                .id(userId)
                .point(10L)
                .build();
        when(userPointRepository.getById(userId)).thenReturn(expected);

        // when
        UserPoint actual = pointService.getById(1L);

        // then
        assertThat(expected.getId()).isEqualTo(actual.getId());
        assertThat(expected.getPoint()).isEqualTo(actual.getPoint());

    }

    @Test
    void 포인트를_충전하면_그만큼_잔액이_추가한다() {
        // given
        long userId = 1L;
        long initialPoint = 10L;
        long chargePoint = 20L;
        long chargedPoint = initialPoint + chargePoint;

        UserPoint initialUserPoint = UserPoint.builder().id(userId).point(initialPoint).build();
        UserPoint willChargeUserPoint = UserPoint.builder().id(userId).point(chargePoint).build();
        UserPoint chargedUserPoint = UserPoint.builder().id(userId).point(chargedPoint).build();

        when(userPointRepository.getById(userId)).thenReturn(initialUserPoint);
        when(userPointRepository.save(any(UserPoint.class)))
                .thenReturn(chargedUserPoint);

        // when
        UserPoint actual = pointService.charge(willChargeUserPoint);

        // then
        assertThat(actual.getId()).isEqualTo(userId);
        assertThat(actual.getPoint()).isEqualTo(chargedPoint);
    }

    @Test
    void 포인트를_사용하면_그만큼_잔액이_차감된다() {
        // given
        long userId = 1L;
        long initialPoint = 30L;
        long usePoint = 20L;
        long usedPoint = initialPoint - usePoint;

        UserPoint initialUserPoint = UserPoint.builder().id(userId).point(initialPoint).build();
        UserPoint willUseUserPoint = UserPoint.builder().id(userId).point(usePoint).build();
        UserPoint usedUserPoint = UserPoint.builder().id(userId).point(usedPoint).build();

        when(userPointRepository.getById(userId)).thenReturn(initialUserPoint);
        when(userPointRepository.save(any(UserPoint.class))).thenReturn(usedUserPoint);

        // when
        UserPoint actual = pointService.charge(willUseUserPoint);

        // then
        assertThat(actual.getId()).isEqualTo(userId);
        assertThat(actual.getPoint()).isEqualTo(usedPoint);
    }

    @Test
    void userId로_포인트_이력을_조회하면_포인트_이력_리스트를_반환한다() {
        // given
        long userId = 1L;
        PointHistory pointHistory = PointHistory.builder().userId(userId).amount(10L).type(TransactionType.CHARGE).updateMillis(1L).build();
        when(pointHistoryRepository.getByUserId(userId)).thenReturn(List.of(pointHistory));

        // when
        List<PointHistory> pointHistories = pointService.getHistoriesByUserId(userId);

        // then
        assertThat(pointHistories).isNotNull();
    }

}
