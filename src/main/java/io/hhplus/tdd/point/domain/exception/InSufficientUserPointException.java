package io.hhplus.tdd.point.domain.exception;

import io.hhplus.tdd.common.exception.CustomException;

public class InSufficientUserPointException extends CustomException {
    public InSufficientUserPointException() {
        super("사용 가능한 포인트가 부족합니다.");
    }
}
