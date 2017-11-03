package me.marichely.Rollin.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

import java.nio.charset.Charset;

public class RestException extends HttpStatusCodeException {
    public RestException(HttpStatus statusCode) {
        super(statusCode);
    }

    public RestException(HttpStatus statusCode, String statusText) {
        super(statusCode, statusText);
    }

    public RestException(HttpStatus statusCode, String statusText, byte[] responseBody, Charset responseCharset) {
        super(statusCode, statusText, responseBody, responseCharset);
    }

    public RestException(HttpStatus statusCode, String statusText, HttpHeaders responseHeaders, byte[] responseBody, Charset responseCharset) {
        super(statusCode, statusText, responseHeaders, responseBody, responseCharset);
    }
}
