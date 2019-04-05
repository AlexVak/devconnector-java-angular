package com.alexvak.devconnectorrest.controller;

import com.alexvak.devconnectorrest.exception.DuplicateUserFoundException;
import com.alexvak.devconnectorrest.exception.FieldValidationError;
import com.alexvak.devconnectorrest.exception.FieldValidationErrorDetails;
import com.alexvak.devconnectorrest.exception.MessageType;
import com.alexvak.devconnectorrest.payload.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    private final MessageSource messageSource;

    public ControllerExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(DuplicateUserFoundException.class)
    public ResponseEntity<ApiResponse> handleDuplicateUserFoundException(Exception exception) {
        return new ResponseEntity<>(new ApiResponse(MessageType.ERROR, exception.getMessage()),
                HttpStatus.CONFLICT);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<FieldValidationErrorDetails> handleMethodArgumentNotValidException(MethodArgumentNotValidException mNotValidException,
                                                                                             HttpServletRequest request) {
        FieldValidationErrorDetails fErrorDetails = new FieldValidationErrorDetails();

        fErrorDetails.setTimeStamp(System.currentTimeMillis());
        fErrorDetails.setStatus(HttpStatus.BAD_REQUEST.value());
        fErrorDetails.setTitle("Field Validation Error");
        fErrorDetails.setDetail("Input Field Validation Failed");
        fErrorDetails.setPath(request.getRequestURI());

        BindingResult result = mNotValidException.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        for (FieldError error : fieldErrors) {
            FieldValidationError fError = processFieldError(error);
            List<FieldValidationError> fValidationErrorsList =
                    fErrorDetails.getErrors().get(error.getField());

            if (Objects.isNull(fValidationErrorsList)) {
                fValidationErrorsList = new ArrayList<>();
            }

            fValidationErrorsList.add(fError);
            fErrorDetails.getErrors().put(error.getField(), fValidationErrorsList);
        }
        return new ResponseEntity<>(fErrorDetails, HttpStatus.BAD_REQUEST);
    }

    private FieldValidationError processFieldError(final FieldError error) {
        FieldValidationError fieldValidationError =
                new FieldValidationError();
        if (error != null) {
            Locale currentLocale = LocaleContextHolder.getLocale();
//            String msg = messageSource.getMessage(error.getDefaultMessage(), null, currentLocale);
            fieldValidationError.setField(error.getField());
            fieldValidationError.setMessageType(MessageType.ERROR);
            fieldValidationError.setMessage(error.getDefaultMessage());
        }
        return fieldValidationError;
    }
}
