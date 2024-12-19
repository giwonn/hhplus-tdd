package io.hhplus.tdd.point.service;

import io.hhplus.tdd.point.domain.PointHistory;
import io.hhplus.tdd.point.domain.TransactionType;
import io.hhplus.tdd.point.domain.UserPoint;
import io.hhplus.tdd.point.service.port.PointHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointHistoryService {

    private final PointHistoryRepository pointHistoryRepository;

    public List<PointHistory> getByUserId(long userId) {
        return pointHistoryRepository.getByUserId(userId);
    }

    public PointHistory addCharged(UserPoint userPoint) {
        return pointHistoryRepository.insert(PointHistory.fromUserPoint(userPoint, TransactionType.CHARGE));
    }

    public PointHistory addUsed(UserPoint userPoint) {
        return pointHistoryRepository.insert(PointHistory.fromUserPoint(userPoint, TransactionType.USE));
    }

}
