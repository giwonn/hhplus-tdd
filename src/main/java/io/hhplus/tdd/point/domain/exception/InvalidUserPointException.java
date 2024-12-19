package io.hhplus.tdd.point.domain.exception;

import io.hhplus.tdd.common.exception.CustomException;

public class InvalidUserPointException extends CustomException {
    public InvalidUserPointException() {
        super("유효하지 않은 포인트 값입니다.");
    }
}
