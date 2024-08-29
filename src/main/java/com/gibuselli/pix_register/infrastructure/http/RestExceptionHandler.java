package com.gibuselli.pix_register.infrastructure.http;

import com.gibuselli.pix_register.infrastructure.exception.*;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;
import java.util.Optional;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String ERROR_VALIDATION_MESSAGE = "Ocorreram erros na validação dos dados";

    private static final String ERROR_DESSERIALIZATION_MESSAGE = "Ocorreram erros na desserialização dos dados";

    private static final String ERROR_GENERIC = "Ocorreram erros na requisição. Confira sua solicitação e tente novamente.";

    private static final String DETAILS = "detail";


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException ex,
            final HttpHeaders headers,
            final HttpStatusCode status,
            final WebRequest request) {
        final var result = ex.getBindingResult();
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(getValidationErrors(result));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(
            HttpMediaTypeNotAcceptableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        final var msg =
                Optional.ofNullable(NestedExceptionUtils.getRootCause(ex))
                        .map(Throwable::getMessage)
                        .orElse(ERROR_GENERIC);

        final var errorResponse =
                new ErrorsResponse(
                        ERROR_DESSERIALIZATION_MESSAGE,
                        Map.of(DETAILS, msg));

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        if (ex instanceof MethodArgumentTypeMismatchException) {
            final MethodArgumentTypeMismatchException exception =
                    (MethodArgumentTypeMismatchException) ex;

            final var errorResponse =
                    new ErrorsResponse(
                            ERROR_DESSERIALIZATION_MESSAGE,
                            Map.of(exception.getName(), exception.getMessage()));

            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
        }
        return super.handleTypeMismatch(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        final var msg =
                Optional.ofNullable(NestedExceptionUtils.getRootCause(ex))
                        .map(Throwable::getMessage)
                        .orElse(ERROR_GENERIC);

        final var errorResponse =
                new ErrorsResponse(
                        ERROR_DESSERIALIZATION_MESSAGE,
                        Map.of(DETAILS, msg));

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
    }

    @ExceptionHandler({
            CnpjOrCpfAlreadyRegistered.class,
            InvalidKeyForNaturalPersonException.class,
            MaxKeysException.class,
            PixKeyAlreadyExistsException.class,
            InvalidKeyTypeException.class,
            InvalidPersonTypeException.class,
            InvalidAccountTypeException.class,
            DisabledPixException.class,
            InvalidSearchRequestException.class,
            AccountAlreadyExistsException.class
    })
    protected ResponseEntity<ErrorsResponse> handlePixKeyValidations(final RuntimeException ex) {
        final var errorResponse =
                new ErrorsResponse(ex.getMessage(), Map.of(DETAILS, ex.getMessage()));

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
    }

    @ExceptionHandler({
            PixKeyNotFoundException.class
    })
    protected ResponseEntity<ErrorsResponse> handleNotFoundResources(final RuntimeException ex) {
        final var errorResponse =
                new ErrorsResponse(ex.getMessage(), Map.of(DETAILS, ex.getMessage()));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }


    private static ErrorsResponse getValidationErrors(final BindingResult bindingResult) {
        ErrorsResponse errorsResponse = new ErrorsResponse();
        errorsResponse.setMessage(ERROR_VALIDATION_MESSAGE);

        bindingResult.getAllErrors()
                .forEach(
                        error -> {
                            final String field;

                            if ("Chave Pix inválida.".equalsIgnoreCase(error.getDefaultMessage())) {
                                return;
                            }

                            if (error instanceof FieldError) {
                                field = ((FieldError) error).getField();
                            } else if ("pixRegisterRequest".equalsIgnoreCase(error.getObjectName())) {
                                field = "key";
                            } else {
                                field = error.getObjectName();
                            }
                            errorsResponse.addErrors(field, error.getDefaultMessage());
                        }
                );

        return errorsResponse;
    }
}
