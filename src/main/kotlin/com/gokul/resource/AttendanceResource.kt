package com.gokul.resource

import com.gokul.dto.CheckInRequest
import com.gokul.dto.CheckOutRequest
import com.gokul.model.Employee
import com.gokul.service.EmployeeManager
import jakarta.ws.rs.PATCH
import javax.validation.Valid
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/attendance")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class AttendanceResource(
    private val employeeManager: EmployeeManager
) {

    @POST
    @Path("/checkin")
    fun checkIn(@Valid request: CheckInRequest): Response {
        val result = employeeManager.checkIn(request)
        return Response.ok(result).build()
    }

    @POST
    @Path("/checkout")
    fun checkOut(@Valid request: CheckOutRequest): Response {
        val result = employeeManager.checkOut(request)
        return Response.ok(result).build()
    }
}