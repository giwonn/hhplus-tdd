package io.hhplus.tdd.point.controller;

import io.hhplus.tdd.point.controller.port.in.ChargePointRequest;
import io.hhplus.tdd.point.controller.port.in.UsePointRequest;
import io.hhplus.tdd.point.controller.port.out.PointHistoryResponse;
import io.hhplus.tdd.point.controller.port.out.UserPointResponse;
import io.hhplus.tdd.point.domain.UserPoint;
import io.hhplus.tdd.point.facade.PointFacade;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/point")
public class PointController {

    private static final Logger log = LoggerFactory.getLogger(PointController.class);

    private final PointFacade pointFacade;

    /**
     * TODO - 특정 유저의 포인트를 조회하는 기능을 작성해주세요.
     */
    @GetMapping("{id}")
    public UserPointResponse point(
            @PathVariable long id
    ) {
        UserPoint userPoint = pointFacade.getById(id);
        return UserPointResponse.from(userPoint);
    }

    /**
     * TODO - 특정 유저의 포인트 충전/이용 내역을 조회하는 기능을 작성해주세요.
     */
    @GetMapping("{id}/histories")
    public List<PointHistoryResponse> history(
            @PathVariable long id
    ) {
        return pointFacade.getHistoriesByUserId(id).stream()
                .map(PointHistoryResponse::from)
                .toList();
    }

    /**
     * TODO - 특정 유저의 포인트를 충전하는 기능을 작성해주세요.
     */
    @PatchMapping("{id}/charge")
    public UserPointResponse charge(
            @PathVariable long id,
            @RequestBody ChargePointRequest request
    ) {
        return UserPointResponse.from(pointFacade.charge(request.toDomain(id)));
    }

    /**
     * TODO - 특정 유저의 포인트를 사용하는 기능을 작성해주세요.
     */
    @PatchMapping("{id}/use")
    public UserPointResponse use(
            @PathVariable long id,
            @RequestBody UsePointRequest request
    ) {

        return UserPointResponse.from(pointFacade.use(request.toDomain(id)));
    }
}
