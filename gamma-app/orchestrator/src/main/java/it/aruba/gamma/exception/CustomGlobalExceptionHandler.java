package it.aruba.gamma.exception;

import it.aruba.gamma.model.ServiceResponseWrapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;


@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @SuppressWarnings({"rawtypes", "unchecked"})
    @ExceptionHandler(BusinessServiceException.class)
    public final ResponseEntity<ServiceResponseWrapper> handleBusinessServiceException(BusinessServiceException ex, WebRequest request) {
        logger.error("Catturato errore gestito per la request " + request, ex);

        ServiceResponseWrapper serviceResponseWrapper = new ServiceResponseWrapper();
        serviceResponseWrapper.setSuccessful(false);
        serviceResponseWrapper.setSuccessMessage(ex.getLocalizedMessage());
        serviceResponseWrapper.setResult(ex.getErrorMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(serviceResponseWrapper);

    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return buildErrorResponse(ex, headers, status);
    }

    @SuppressWarnings("rawtypes")
    private static ResponseEntity<Object> buildErrorResponse(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status) {
        ServiceResponseWrapper esito = new ServiceResponseWrapper();

        handleMethodArgumentViolations(ex, esito);
        esito.setSuccessful(false);
        esito.setHttpErrorCode(status.value());

        return new ResponseEntity<>(esito, headers, status);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void handleMethodArgumentViolations(MethodArgumentNotValidException ex, ServiceResponseWrapper esito) {
        // Get all errors
        List<String> validationErrors = ex.getBindingResult().getAllErrors().stream()
                .map(CustomGlobalExceptionHandler::resolveErrorMessage).collect(Collectors.toList());

        esito.setValidationErrors(validationErrors);
    }

    @SuppressWarnings("rawtypes")
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ServiceResponseWrapper esito = new ServiceResponseWrapper();
        esito.setSuccessful(false);
        esito.setHttpErrorCode(HttpStatus.BAD_REQUEST.value());

        if (BusinessServiceException.class.isAssignableFrom(ex.getClass())) {
            esito.setErrorMessage(BusinessServiceException.class.cast(ex).getErrorMessage());
        } else {
            esito.setErrorMessage(ErrorMessage.builder().code("E001").build());
        }

        return new ResponseEntity<>(esito, HttpStatus.BAD_REQUEST);
    }


    @SuppressWarnings("rawtypes")
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ServiceResponseWrapper> constraintViolationException(ConstraintViolationException ex) {

        ServiceResponseWrapper esito = new ServiceResponseWrapper();
        esito.setSuccessful(false);
        esito.setHttpErrorCode(HttpStatus.BAD_REQUEST.value());

        handleConstraintViolations(ex, esito);

        return new ResponseEntity<>(esito, HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void handleConstraintViolations(ConstraintViolationException ex, ServiceResponseWrapper esito) {
        List<String> validationErrors = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        esito.setValidationErrors(validationErrors);
    }

    private static String resolveErrorMessage(ObjectError fieldError) {

        if (FieldError.class.isAssignableFrom(fieldError.getClass())) {
            if (fieldError.getArguments() != null && fieldError.getArguments().length > 0) {

                return MessageFormat.format(fieldError.getDefaultMessage(), fieldError.getArguments());
            }

            return fieldError.getDefaultMessage();
        }

        return fieldError.getDefaultMessage();
    }

}
