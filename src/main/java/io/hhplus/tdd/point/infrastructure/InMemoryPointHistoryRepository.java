package io.hhplus.tdd.point.infrastructure;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.point.domain.PointHistory;
import io.hhplus.tdd.point.service.port.PointHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class InMemoryPointHistoryRepository implements PointHistoryRepository {

    private final PointHistoryTable pointHistoryTable;

    @Override
    public List<PointHistory> getByUserId(long id) {
        return pointHistoryTable.selectAllByUserId(id)
                .stream()
                .map(PointHistoryEntity::toDomain)
                .toList();
    }

    @Override
    public PointHistory insert(PointHistory pointHistory) {
        return pointHistoryTable.insert(
                pointHistory.getUserId(),
                pointHistory.getAmount(),
                pointHistory.getType(),
                pointHistory.getUpdateMillis()
        ).toDomain();
    }
}
