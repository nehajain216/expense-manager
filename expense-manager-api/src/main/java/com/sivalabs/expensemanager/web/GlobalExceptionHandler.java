package com.sivalabs.expensemanager.web;

import com.sivalabs.expensemanager.exceptions.ErrorResponse;
import com.sivalabs.expensemanager.exceptions.TransactionNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.zalando.problem.Problem;
import org.zalando.problem.StatusType;
import org.zalando.problem.spring.common.HttpStatusAdapter;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler(TransactionNotFoundException.class)
//    public ResponseEntity<ErrorResponse> handleTransactionNotFoundException(
//            TransactionNotFoundException e) {
//        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), 400);
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
//    }

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<Problem> handleTransactionNotFoundException(
        TransactionNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), 400);
        Problem problem = Problem.builder()
            .withStatus(new HttpStatusAdapter(HttpStatus.BAD_REQUEST))
            .withTitle("Transaction Not found Error")
            .withDetail(e.getMessage())
            .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
    }
}
