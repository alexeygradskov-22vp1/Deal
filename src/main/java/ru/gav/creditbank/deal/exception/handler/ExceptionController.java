package ru.gav.creditbank.deal.exception.handler;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.gav.creditbank.deal.exception.*;

@Slf4j
@ControllerAdvice
@Hidden
public class ExceptionController {
    private final String COMMUNICATION_EXCEPTION = "Произошла ошибка не сервере, пожалуйста обратитесь к администратору";

    @ExceptionHandler
    private ResponseEntity<String> webClientError(WebClientReturnNullException webClientReturnNullException){
        log.error(webClientReturnNullException.getMessage());
        return ResponseEntity.internalServerError().body(COMMUNICATION_EXCEPTION);
    }

    @ExceptionHandler
    private void noSuchEnumValueExceptionHandler(NoSuchEnumValue enumValue){
        log.error(enumValue.getMessage());
    }

    @ExceptionHandler
    private ResponseEntity<String> noSuchElementInDBHandler(NoSuchElementInDatabaseException e){
        log.info(e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler
    private ResponseEntity<String> scoringDataExceptionHandler(ScoringDataException scoringDataException){
        log.error(scoringDataException.getMessage());
        return ResponseEntity.internalServerError().body(COMMUNICATION_EXCEPTION);
    }

    @ExceptionHandler
    private ResponseEntity<String> calcLoanOffersExceptionHandler(CalculateLoanOffersException calculateLoanOffersException){
        log.error(calculateLoanOffersException.getMessage());
        return ResponseEntity.internalServerError().body(COMMUNICATION_EXCEPTION);
    }
}
