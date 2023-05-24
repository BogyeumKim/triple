package com.gaerine.triple.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "error.duplicate")
public class PlaceDupleException extends RuntimeException{
}
