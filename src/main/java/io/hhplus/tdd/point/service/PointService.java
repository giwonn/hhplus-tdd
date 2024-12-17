package io.hhplus.tdd.point.service;

import io.hhplus.tdd.point.domain.TransactionType;
import io.hhplus.tdd.point.domain.PointHistory;
import io.hhplus.tdd.point.domain.UserPoint;
import io.hhplus.tdd.point.service.port.PointHistoryRepository;
import io.hhplus.tdd.point.service.port.UserPointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointService {

    private final UserPointRepository userPointRepository;
    private final PointHistoryRepository pointHistoryRepository;

    public UserPoint getById(long id) {
        return userPointRepository.getById(id);
    }

    public UserPoint charge(UserPoint userPoint) {
        UserPoint remainingUserPoint = userPointRepository.getById(userPoint.getId());
        remainingUserPoint.charge(userPoint.getPoint());
        UserPoint chargedUserPoint = userPointRepository.save(remainingUserPoint);

        pointHistoryRepository.insert(PointHistory.fromUserPoint(userPoint, TransactionType.CHARGE)); // TODO: 이거 사이드 이펙트임...

        return chargedUserPoint;
    }

    public UserPoint use(UserPoint userPoint) {
        UserPoint remainingUserPoint = userPointRepository.getById(userPoint.getId());
        remainingUserPoint.use(userPoint.getPoint());
        UserPoint usedUserPoint = userPointRepository.save(userPoint);

        pointHistoryRepository.insert(PointHistory.fromUserPoint(userPoint, TransactionType.CHARGE)); // TODO: 이것도 사이트 이펙트

        return usedUserPoint;
    }

    public List<PointHistory> getHistoriesByUserId(long userId) {
        return pointHistoryRepository.getByUserId(userId);
    }

}
