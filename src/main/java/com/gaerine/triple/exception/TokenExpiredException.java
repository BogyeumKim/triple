package com.gaerine.triple.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "error.unauthorized")
public class TokenExpiredException extends RuntimeException{
}
