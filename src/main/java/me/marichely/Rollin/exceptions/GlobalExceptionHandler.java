package me.marichely.Rollin.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    private class JsonResponse {
        String message;
        private HttpStatus httpStatus;
        Integer httpStatusCode;


        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
        private LocalDateTime timestamp;

        public JsonResponse() {
            timestamp = LocalDateTime.now();
        }

        public JsonResponse(String message) {
            this();
            this.message = message;
        }

        public JsonResponse(HttpStatus httpStatus) {
            this();
            this.httpStatus = httpStatus;
            this.httpStatusCode = httpStatus.value();
        }

        public JsonResponse(String message, HttpStatus httpStatus) {
            this();
            this.message = message;
            this.httpStatus = httpStatus;
            this.httpStatusCode = httpStatus.value();
        }


        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public HttpStatus getHttpStatus() {
            return httpStatus;
        }

        public void setHttpStatus(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
        }

        public Integer getHttpStatusCode() {
            return httpStatusCode;
        }
    }

    @ExceptionHandler(value = RestException.class)
    public ResponseEntity<JsonResponse> handleException(RestException e) {
        return new ResponseEntity<JsonResponse>(new JsonResponse(e.getMessage(),e.getStatusCode()), HttpStatus.NOT_FOUND);
    }


}