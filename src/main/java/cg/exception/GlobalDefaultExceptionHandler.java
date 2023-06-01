package cg.exception;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@AllArgsConstructor
public class GlobalDefaultExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        Map<Object, Object> errors;
        BindingResult bindingResult = ex.getBindingResult();
        errors = bindingResult.getFieldErrors()
                .stream()
                .collect(Collectors
                        .toMap(FieldError::getField,
                                FieldError::getDefaultMessage));
        return new ResponseEntity<>(
                new Result<>(
                        errors,
                        HttpStatus.BAD_REQUEST.value()
                ),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ValidationException.class, DataInputException.class})
    public ResponseEntity<?> validationException(BaseException ex, Locale locale) {
        String message = ex.getMessage();
        if (message != null)
            message = messageSource.getMessage(ex.getMessage(), new Object[0], locale);
        Map<String, String> errors = ex.getErrors();
        if (ex.getErrors() != null)
            errors = errors
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            entry -> messageSource.getMessage(entry.getValue(), new Object[0], locale))
                    );
        return new ResponseEntity<>(
                new Result(
                        message,
                        errors,
                        ex.status
                )
                , HttpStatus.valueOf(ex.status));
    }


}
