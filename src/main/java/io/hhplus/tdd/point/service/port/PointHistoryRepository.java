package io.hhplus.tdd.point.service.port;

import io.hhplus.tdd.point.domain.PointHistory;

import java.util.List;

public interface PointHistoryRepository {

    List<PointHistory> getByUserId(long id);

    PointHistory insert(PointHistory pointHistory);

}
