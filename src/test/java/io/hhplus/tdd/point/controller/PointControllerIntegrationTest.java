package io.hhplus.tdd.point.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.hhplus.tdd.point.controller.port.in.ChargePointRequest;
import io.hhplus.tdd.point.controller.port.in.UsePointRequest;
import io.hhplus.tdd.point.domain.UserPoint;
import io.hhplus.tdd.point.facade.PointFacade;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PointControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PointFacade pointFacade;

    @Nested
    class 성공_테스트 {

        @Test
        void 포인트_조회() throws Exception {
            // given
            UserPoint userPoint = UserPoint.builder().id(1L).point(10L).build();
            pointFacade.charge(userPoint);

            // when & then
            mockMvc.perform(get(String.format("/point/%d", userPoint.getId())))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(userPoint.getId()))
                    .andExpect(jsonPath("$.point").value(userPoint.getPoint()));
        }

        @Test
        void 포인트_충전() throws Exception {
            // given
            ChargePointRequest request = ChargePointRequest.builder().amount(10L).build();

            // when & then
            mockMvc.perform(patch(String.format("/point/%d/charge", 2L))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(2L))
                    .andExpect(jsonPath("$.point").value(10L));
        }

        @Test
        void 포인트_사용() throws Exception {
            // given
            UserPoint userPoint = UserPoint.builder().id(3L).point(10L).build();
            pointFacade.charge(userPoint);

            UsePointRequest request = UsePointRequest.builder().amount(10L).build();

            // when & then
            mockMvc.perform(patch(String.format("/point/%d/use", userPoint.getId()))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(userPoint.getId()))
                    .andExpect(jsonPath("$.point").value(0L));
        }

        @Test
        void 포인트_이력_조회() throws Exception {
            // given
            UserPoint userPoint = UserPoint.builder().id(1L).point(10L).build();
            pointFacade.charge(userPoint);

            UsePointRequest request = UsePointRequest.builder().amount(10L).build();

            // when & then
            mockMvc.perform(patch(String.format("/point/%d/use", userPoint.getId()))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(userPoint.getId()))
                    .andExpect(jsonPath("$.point").value(0L));
        }

    }

}
