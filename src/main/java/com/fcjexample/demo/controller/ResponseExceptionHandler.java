/*************************************************************************
 *
 * Copyright (c) 2016, DATAVISOR, INC.
 * All rights reserved.
 * __________________
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of DataVisor, Inc.
 * The intellectual and technical concepts contained
 * herein are proprietary to DataVisor, Inc. and
 * may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from DataVisor, Inc.
 */

package com.fcjexample.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fcjexample.demo.model.ApiException;
import com.fcjexample.demo.util.ValidationError;
import com.fcjexample.demo.util.ValidationErrorBuilder;
import com.fcjexample.demo.util.enums.ApiStatus;
import com.fcjexample.demo.util.exception.DataViewException;
import com.fcjexample.demo.util.exception.ViewTypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Set;

//@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
//public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {
public class ResponseExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseExceptionHandler.class);
    private final ObjectMapper mapper = new ObjectMapper();

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<ApiException> handleAllCustomException(Exception ex,
            WebRequest request) {
        ApiException apiException = new ApiException(
                ApiStatus.FP_API_ERROR_DEFAULT_ERROR.toString(),
                ex.getMessage(), ex.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.set("fixAdvice", "check allxxx please");
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return new ResponseEntity<>(apiException, headers, status);
    }

    @ExceptionHandler(value = { DataViewException.class, ViewTypeException.class })
    protected ResponseEntity<ApiException> handleViewTypeException(Exception ex,
            WebRequest request) {
        ApiException apiException = new ApiException(ApiStatus.FP_API_ERROR_VIEW_ERROR.toString(),
                ex.getMessage(), ex.toString());

        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        // todo

        return new ResponseEntity<>(apiException, headers, status);
    }

    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    protected ResponseEntity<ApiException> handleValidationException(Exception ex,
            WebRequest request) throws Exception {
        ApiException apiException = new ApiException();

        ValidationError validationError = ValidationErrorBuilder
                .fromBindingErrors(((MethodArgumentNotValidException) ex).getBindingResult());
        apiException.setMsg(mapper.writeValueAsString(validationError));
        apiException.setExInfo(ex.toString());

        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return new ResponseEntity<>(apiException, headers, status);
    }

    /**
     * Provides handling for standard Spring MVC exceptions.
     *
     * @param ex      the target exception
     * @param request the current request
     */
    @ExceptionHandler({
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            HttpMediaTypeNotAcceptableException.class,
            MissingPathVariableException.class,
            MissingServletRequestParameterException.class,
            ServletRequestBindingException.class,
            ConversionNotSupportedException.class,
            TypeMismatchException.class,
            HttpMessageNotReadableException.class,
            HttpMessageNotWritableException.class,
            //            MethodArgumentNotValidException.class,
            MissingServletRequestPartException.class,
            BindException.class,
            NoHandlerFoundException.class,
            AsyncRequestTimeoutException.class
    })
    public ResponseEntity<ApiException> handleExceptionHaha(Exception ex, WebRequest request)
            throws Exception {
        HttpHeaders headers = new HttpHeaders();
        ApiException apiException = new ApiException();
        apiException.setMsg(ex.getMessage());
        apiException.setExInfo(ex.toString());

        if (ex instanceof HttpRequestMethodNotSupportedException) {
            HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
            return handleHttpRequestMethodNotSupported((HttpRequestMethodNotSupportedException) ex,
                    apiException, headers, status, request);
        } else if (ex instanceof HttpMediaTypeNotSupportedException) {
            HttpStatus status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
            return handleStandNormalException(apiException, headers, status, request);
        } else if (ex instanceof HttpMediaTypeNotAcceptableException) {
            HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
            return handleStandNormalException(apiException, headers, status, request);
        } else if (ex instanceof MissingPathVariableException) {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleStandNormalException(apiException, headers, status, request);
        } else if (ex instanceof MissingServletRequestParameterException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return handleStandNormalException(apiException, headers, status, request);
        } else if (ex instanceof ServletRequestBindingException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return handleStandNormalException(apiException, headers, status, request);
        } else if (ex instanceof ConversionNotSupportedException) {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleStandNormalException(apiException, headers, status, request);
        } else if (ex instanceof TypeMismatchException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return handleStandNormalException(apiException, headers, status, request);
        } else if (ex instanceof HttpMessageNotReadableException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return handleStandNormalException(apiException, headers, status, request);
        } else if (ex instanceof HttpMessageNotWritableException) {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleStandNormalException(apiException, headers, status, request);
        } else if (ex instanceof MethodArgumentNotValidException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return handleStandNormalException(apiException, headers, status, request);
        } else if (ex instanceof MissingServletRequestPartException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return handleStandNormalException(apiException, headers, status, request);
        } else if (ex instanceof BindException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return handleStandNormalException(apiException, headers, status, request);
        } else if (ex instanceof NoHandlerFoundException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return handleStandNormalException(apiException, headers, status, request);
        } else if (ex instanceof AsyncRequestTimeoutException) {
            HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
            return handleStandNormalException(apiException, headers, status, request);
        } else {
            throw ex;
        }
    }

    protected ResponseEntity<ApiException> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, ApiException apiException,
            HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        Set<HttpMethod> supportedMethods = ex.getSupportedHttpMethods();
        if (!CollectionUtils.isEmpty(supportedMethods)) {
            headers.setAllow(supportedMethods);
        }
        apiException.setStatusCode(status.toString());
        return handleExceptionInternal(apiException, headers, status, request);
    }

    protected ResponseEntity<ApiException> handleStandNormalException(
            ApiException apiException, HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        apiException.setStatusCode(status.toString());
        return handleExceptionInternal(apiException, headers, status, request);
    }

    protected ResponseEntity<ApiException> handleNoHandlerFoundException(
            ApiException apiException, HttpHeaders headers, HttpStatus status,
            WebRequest request) {

        return handleExceptionInternal(apiException, headers, status, request);
    }

    protected ResponseEntity<ApiException> handleExceptionInternal(
            ApiException apiException, HttpHeaders headers, HttpStatus status,
            WebRequest request) {

        return new ResponseEntity<>(apiException, headers, status);
    }

}