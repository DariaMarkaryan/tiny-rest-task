package com.dins.demo.exceptions;

import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ContactNotFoundException extends NotFoundException {

    public ContactNotFoundException(String message) {
        super(message);
    }
}
