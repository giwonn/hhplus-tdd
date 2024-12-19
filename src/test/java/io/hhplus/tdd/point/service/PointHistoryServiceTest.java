package io.hhplus.tdd.point.service;

import io.hhplus.tdd.point.domain.PointHistory;
import io.hhplus.tdd.point.domain.TransactionType;
import io.hhplus.tdd.point.domain.UserPoint;
import io.hhplus.tdd.point.service.port.PointHistoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PointHistoryServiceTest {

    @Mock
    PointHistoryRepository pointHistoryRepository;


    @InjectMocks
    PointHistoryService pointHistoryService;

    @Test
    void userId로_포인트_이력을_조회하면_포인트_이력_리스트를_반환한다() {
        // given
        long userId = 1L;
        PointHistory pointHistory = PointHistory.builder().userId(userId).amount(10L).type(TransactionType.CHARGE).updateMillis(1L).build();
        when(pointHistoryRepository.getByUserId(userId)).thenReturn(List.of(pointHistory));

        // when
        List<PointHistory> pointHistories = pointHistoryService.getByUserId(userId);

        // then
        assertThat(pointHistories).isNotNull();
    }

    @Test
    void 충전_이력을_기록한다() {
        // given
        UserPoint chargeUserPoint = UserPoint.builder()
                .id(1L)
                .point(10L)
                .build();
        when(pointHistoryRepository.insert(any(PointHistory.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        PointHistory pointHistory = pointHistoryService.addCharged(chargeUserPoint);

        // then
        assertThat(pointHistory.getUserId()).isEqualTo(chargeUserPoint.getId());
        assertThat(pointHistory.getAmount()).isEqualTo(chargeUserPoint.getPoint());
        assertThat(pointHistory.getType()).isEqualTo(TransactionType.CHARGE);
    }

    @Test
    void 사용_이력을_기록한다() {
        // given
        UserPoint chargeUserPoint = UserPoint.builder()
                .id(1L)
                .point(10L)
                .build();
        when(pointHistoryRepository.insert(any(PointHistory.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        PointHistory pointHistory = pointHistoryService.addUsed(chargeUserPoint);

        // then
        assertThat(pointHistory.getUserId()).isEqualTo(chargeUserPoint.getId());
        assertThat(pointHistory.getAmount()).isEqualTo(chargeUserPoint.getPoint());
        assertThat(pointHistory.getType()).isEqualTo(TransactionType.USE);
    }

}
