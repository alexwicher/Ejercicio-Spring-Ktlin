package com.ejerciciosFravega.ejercicio1.handler

import org.springframework.http.HttpStatus

data class ErrorResponse(var status: HttpStatus, var msg:String)