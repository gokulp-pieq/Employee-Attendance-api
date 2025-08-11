package com.gokul.exceptionMapper

import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider
import jakarta.ws.rs.BadRequestException

@Provider
class BadRequestExceptionMapper : ExceptionMapper<BadRequestException> {

    override fun toResponse(exception: BadRequestException): Response {
        val errorResponse = mapOf(
            "code" to 400,
            "message" to (exception.message ?: "Bad Request")
        )

        return Response.status(Response.Status.BAD_REQUEST)
            .entity(errorResponse)
            .type(MediaType.APPLICATION_JSON)
            .build()
    }
}
