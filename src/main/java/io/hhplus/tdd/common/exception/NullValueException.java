package io.hhplus.tdd.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NullValueException extends RuntimeException {
    public NullValueException() {
        super("값은 Null이 될 수 없습니다.");
    }
}
