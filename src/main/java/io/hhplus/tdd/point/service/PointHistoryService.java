package io.hhplus.tdd.point.service;

import io.hhplus.tdd.point.domain.PointHistory;
import io.hhplus.tdd.point.domain.TransactionType;
import io.hhplus.tdd.point.domain.UserPoint;
import io.hhplus.tdd.point.service.port.PointHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
@RequiredArgsConstructor
public class PointHistoryService {

    private final PointHistoryRepository pointHistoryRepository;

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);

    public List<PointHistory> getByUserId(long userId) {
        return withReadLock(() -> pointHistoryRepository.getByUserId(userId));
    }

    public PointHistory addCharged(UserPoint userPoint) {
        return withWriteLock(() -> pointHistoryRepository.insert(PointHistory.fromUserPoint(userPoint, TransactionType.CHARGE)));
    }

    public PointHistory addUsed(UserPoint userPoint) {
        return withWriteLock(() -> pointHistoryRepository.insert(PointHistory.fromUserPoint(userPoint, TransactionType.USE)));
    }

    private <T> T withReadLock(Callable<T> callable) {
        return applyLock(callable, readWriteLock.readLock());
    }

    private <T> T withWriteLock(Callable<T> callable) {
        return applyLock(callable, readWriteLock.writeLock());
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
