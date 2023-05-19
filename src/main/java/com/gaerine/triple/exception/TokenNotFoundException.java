package com.gaerine.triple.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "error.notfound")
public class TokenNotFoundException extends RuntimeException{
}
