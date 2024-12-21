package io.hhplus.tdd.database;

import io.hhplus.tdd.point.infrastructure.UserPointEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 해당 Table 클래스는 변경하지 않고 공개된 API 만을 사용해 데이터를 제어합니다.
 */
@Component
public class UserPointTable {

    private final Map<Long, UserPointEntity> table = new HashMap<>();

    public UserPointEntity selectById(Long id) {
        throttle(200);
        return table.getOrDefault(id, UserPointEntity.empty(id));
    }

    public UserPointEntity insertOrUpdate(long id, long amount) {
        throttle(300);
        UserPointEntity userPoint = new UserPointEntity(id, amount, System.currentTimeMillis());
        table.put(id, userPoint);
        return userPoint;
    }

    private void throttle(long millis) {
        try {
            TimeUnit.MILLISECONDS.sleep((long) (Math.random() * millis));
        } catch (InterruptedException ignored) {

        }
    }
}
