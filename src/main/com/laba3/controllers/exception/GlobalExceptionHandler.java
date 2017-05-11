package com.laba3.controllers.exception;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by root on 10.05.17.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    final static Logger logger = Logger.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "IOException occured")
    @ExceptionHandler(IOException.class)
    public void handleIOException(){
        logger.error("IOException handler executed");
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "SQLException occured")
    @ExceptionHandler(SQLException.class)
    public void handleSQLException(){
        logger.error("SQLException handler executed");
    }

    @ExceptionHandler(Exception.class)
    public void handleException(){
        logger.error("Exception handler executed");
    }
}
