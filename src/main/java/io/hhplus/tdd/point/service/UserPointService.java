package io.hhplus.tdd.point.service;

import io.hhplus.tdd.point.domain.UserPoint;
import io.hhplus.tdd.point.service.port.UserPointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
@RequiredArgsConstructor
public class UserPointService {

    private final UserPointRepository userPointRepository;

    private final Map<Long, ReadWriteLock> locks = new ConcurrentHashMap<>();

    public UserPoint getById(long id) {
        return withReadLock(id, () -> userPointRepository.getById(id));
    }

    public UserPoint charge(UserPoint userPoint) {
        return withWriteLock(userPoint.getId(), () -> {
            UserPoint currentUserPoint = userPointRepository.getById(userPoint.getId()); // 200ms
            currentUserPoint.charge(userPoint.getPoint());
            return userPointRepository.save(currentUserPoint); // 300ms
        });
    }

    public UserPoint use(UserPoint userPoint) {
        return withWriteLock(userPoint.getId(), () -> {
            UserPoint currentUserPoint = userPointRepository.getById(userPoint.getId());
            currentUserPoint.use(userPoint.getPoint());
            return userPointRepository.save(currentUserPoint);
        });
    }

    private <T> T withReadLock(long userId, Callable<T> callable) {
        ReadWriteLock userLock = locks.computeIfAbsent(
                userId,
                id -> new ReentrantReadWriteLock(true)
        );
        return applyLock(callable, userLock.readLock());
    }

    private <T> T withWriteLock(long userId, Callable<T> callable) {
        ReadWriteLock userLock = locks.computeIfAbsent(
                userId,
                id -> new ReentrantReadWriteLock(true)
        );
        return applyLock(callable, userLock.writeLock());
    }

    private <T> T applyLock(Callable<T> callable, Lock lock) {
        lock.lock();

        try {
            return callable.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
