package io.hhplus.tdd.point.service.port;

import io.hhplus.tdd.point.domain.UserPoint;

public interface UserPointRepository {

    UserPoint getById(long id);

    UserPoint save(UserPoint userPoint);

}
