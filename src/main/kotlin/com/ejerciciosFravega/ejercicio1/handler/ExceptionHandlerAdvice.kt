package com.ejerciciosFravega.ejercicio1.handler

import com.fasterxml.jackson.databind.JsonMappingException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.HttpClientErrorException


@ControllerAdvice
class ExceptionHandlerAdvice {

    @ExceptionHandler(HttpClientErrorException.BadRequest::class)
    fun handleJsonMappingExceptionBadRequest(ex: JsonMappingException?): ResponseEntity<ErrorResponse?>? {
        val errorResponse = ErrorResponse(HttpStatus.BAD_REQUEST, "Malformed request, check documentation")
        return ResponseEntity<ErrorResponse?>(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(java.lang.Exception::class)
    fun handleJsonMappingExceptionLast(ex: JsonMappingException?): ResponseEntity<ErrorResponse?>? {
        ex?.printStackTrace()
        val errorResponse =
            ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected server error, send help please!")
        return ResponseEntity<ErrorResponse?>(errorResponse, HttpStatus.BAD_REQUEST)
    }


}