package io.hhplus.tdd.point.facade;

import io.hhplus.tdd.point.domain.PointHistory;
import io.hhplus.tdd.point.domain.UserPoint;
import io.hhplus.tdd.point.service.PointHistoryService;
import io.hhplus.tdd.point.service.UserPointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PointFacade {
    private final UserPointService userPointService;
    private final PointHistoryService pointHistoryService;

    public UserPoint getById(long userId) {
        return userPointService.getById(userId);
    }

    public UserPoint charge(UserPoint userPoint) {
        UserPoint currentUserPoint = userPointService.charge(userPoint);
        pointHistoryService.addCharged(userPoint);
        return currentUserPoint;
    }

    public UserPoint use(UserPoint userPoint) {
        UserPoint currentUserPoint = userPointService.use(userPoint);
        pointHistoryService.addUsed(userPoint);
        return currentUserPoint;
    }

    public List<PointHistory> getHistoriesByUserId(long userId) {
        return pointHistoryService.getByUserId(userId);
    }
}
