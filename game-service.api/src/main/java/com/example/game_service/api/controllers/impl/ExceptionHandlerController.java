package com.example.game_service.api.controllers.impl;

import com.example.game_service.api.commons.dto.ErrorResponse;
import com.example.game_service.api.commons.exceptions.GameException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerController {
    @ExceptionHandler(value ={GameException.class})
    ResponseEntity<ErrorResponse>handleError(GameException gameException){
       log.error("new exception", gameException);
        var errorResponse = ErrorResponse.builder()
                .codeStatus(gameException.getHttpStatus().value())
                .message(gameException.getMessage())
                .build();

        return ResponseEntity.status(gameException.getHttpStatus()).body(errorResponse);
    }
}
