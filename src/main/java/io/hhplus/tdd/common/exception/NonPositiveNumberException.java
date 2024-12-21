package io.hhplus.tdd.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NonPositiveNumberException extends RuntimeException {
    public NonPositiveNumberException() {
        super("값이 양수가 아닙니다.");
    }
}
