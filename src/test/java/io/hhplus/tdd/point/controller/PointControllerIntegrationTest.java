package io.hhplus.tdd.point.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.hhplus.tdd.point.controller.port.in.ChargePointRequest;
import io.hhplus.tdd.point.controller.port.in.UsePointRequest;
import io.hhplus.tdd.point.controller.port.out.PointHistoryResponse;
import io.hhplus.tdd.point.controller.port.out.UserPointResponse;
import io.hhplus.tdd.point.domain.UserPoint;
import io.hhplus.tdd.point.facade.PointFacade;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@AutoConfigureMockMvc
public class PointControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PointFacade pointFacade;

    @Autowired
    private PointController pointController;

    @Nested
    class 성공_테스트 {

        @Test
        void 포인트_조회() {
            // given
            long userId = 1L;
            long point = 10L;
            UserPoint userPoint = UserPoint.builder().id(userId).point(point).build();
            pointFacade.charge(userPoint);

            // when
            UserPointResponse resp = pointController.point(1L);

            // then
            assertThat(resp.getId()).isEqualTo(userId);
            assertThat(resp.getPoint()).isEqualTo(point);
        }

        @Test
        void 포인트_충전() {
            // given
            long userId = 2L;
            long point = 10L;
            ChargePointRequest req = ChargePointRequest.builder().amount(point).build();

            // when
            UserPointResponse resp = pointController.charge(userId, req);

            // then
            assertThat(resp.getId()).isEqualTo(userId);
            assertThat(resp.getPoint()).isEqualTo(point);
        }

        @Test
        void 포인트_사용() {
            // given
            long userId = 3L;
            long point = 10L;
            UserPoint userPoint = UserPoint.builder().id(userId).point(point).build();
            pointFacade.charge(userPoint);

            UsePointRequest req = UsePointRequest.builder().amount(point).build();

            // when
            UserPointResponse resp = pointController.use(userId, req);

            // then
            assertThat(resp.getId()).isEqualTo(userId);
            assertThat(resp.getPoint()).isZero();
        }

        @Test
        void 포인트_이력_조회() {
            // given
            long userId = 4L;
            long point = 10L;
            UserPoint userPoint = UserPoint.builder().id(userId).point(point).build();
            pointFacade.charge(userPoint);

            // when
            List<PointHistoryResponse> resp = pointController.history(userId);

            // then
            assertThat(resp.get(0).getUserId()).isEqualTo(userId);
            assertThat(resp.get(0).getAmount()).isEqualTo(point);

        }

    }

    @Nested
    class 동시성_테스트 {

        @Test
        void 동일ID로_충전_요청이_동시에_들어온다() throws Exception {
            // given
            long userId = 5L;
            long chargeAmount = 1L;
            int requestCount = 100;

            List<Callable<Void>> tasks = new ArrayList<>();
            for (int i = 0; i < requestCount; i++) {
                tasks.add(() -> {
                    pointFacade.charge(UserPoint.builder().id(userId).point(chargeAmount).build());
                    return null;
                });
            }

            ExecutorService executorService = Executors.newFixedThreadPool(requestCount);

            // when
            List<Future<Void>> futures = executorService.invokeAll(tasks);
            for (Future<Void> future : futures) {
                future.get();
            }

            executorService.shutdown();

            // then
            UserPointResponse userPoint = pointController.point(userId);
            assertThat(userPoint.getId()).isEqualTo(userId);
            assertThat(userPoint.getPoint()).isEqualTo(chargeAmount * requestCount);
        }

        @Test
        void 여러ID로_충전_요청이_동시에_들어온다() throws Exception {
            // given
            long userId = 6L;
            long chargeAmount = 6L;
            int requestCount = 100;

            List<Callable<Void>> tasks = new ArrayList<>();
            for (int i = 0; i < requestCount; i++) {
                UserPoint userPoint = UserPoint.builder().id(userId++).point(chargeAmount++).build();
                tasks.add(() -> {
                    pointFacade.charge(userPoint);
                    return null;
                });
            }

            ExecutorService executorService = Executors.newFixedThreadPool(requestCount);

            // when
            List<Future<Void>> futures = executorService.invokeAll(tasks);
            for (Future<Void> future : futures) {
                future.get();
            }

            executorService.shutdown();

            // then
            for (long i = 6L; i < requestCount + 6L; i++) {
                List<PointHistoryResponse> resp = pointController.history(i);
                final long result = i;
                assertAll(
                        () -> assertThat(resp).hasSize(1),
                        () -> assertThat(resp.get(0).getUserId()).isEqualTo(result),
                        () -> assertThat(resp.get(0).getAmount()).isEqualTo(result)
                );
            }
        }

    }
}
