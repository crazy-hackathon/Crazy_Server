package com.project.crazy.global.exception;

import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
public class ExceptionDto {

    private final String dateTime = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss")
            .format(new Date());

    private final String message;

    public ExceptionDto(String message) {
        this.message = message;
    }
}
