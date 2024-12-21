package io.hhplus.tdd.point.infrastructure;

import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.domain.UserPoint;
import io.hhplus.tdd.point.service.port.UserPointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class InMemoryUserPointRepository implements UserPointRepository {

    private final UserPointTable userPointTable;

    @Override
    public UserPoint getById(long id) {
        return userPointTable.selectById(id).toDomain();
    }

    @Override
    public UserPoint save(UserPoint userPoint) {
        return userPointTable.insertOrUpdate(userPoint.getId(), userPoint.getPoint()).toDomain();
    }

}
