package io.hhplus.tdd.point.domain.exception;

import io.hhplus.tdd.common.exception.CustomException;

public class ExceedUserPointLimitException extends CustomException {
    public ExceedUserPointLimitException() {
        super("저장 가능한 포인트를 초과하였습니다.");
    }
}
