package io.hhplus.tdd.point.service;

import io.hhplus.tdd.point.domain.UserPoint;
import io.hhplus.tdd.point.service.port.UserPointRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserPointServiceTest {

    @Mock
    UserPointRepository userPointRepository;


    @InjectMocks
    UserPointService pointService;

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

}
