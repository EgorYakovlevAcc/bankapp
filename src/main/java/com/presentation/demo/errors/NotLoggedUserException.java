package com.presentation.demo.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED,reason = "Not logged in.")
public class NotLoggedUserException extends MessageException{

}
