package io.hhplus.tdd.point.service;

import io.hhplus.tdd.point.domain.UserPoint;
import io.hhplus.tdd.point.service.port.UserPointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPointService {

    private final UserPointRepository userPointRepository;

    public UserPoint getById(long id) {
        return userPointRepository.getById(id);
    }

    public UserPoint charge(UserPoint userPoint) {
        UserPoint currentUserPoint = userPointRepository.getById(userPoint.getId());
        currentUserPoint.charge(userPoint.getPoint());
        return userPointRepository.save(currentUserPoint);
    }

    public UserPoint use(UserPoint userPoint) {
        UserPoint currentUserPoint = userPointRepository.getById(userPoint.getId());
        currentUserPoint.use(userPoint.getPoint());
        return userPointRepository.save(currentUserPoint);
    }

}
