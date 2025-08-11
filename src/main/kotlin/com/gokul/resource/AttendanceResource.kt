package com.gokul.resource

import com.gokul.dto.CheckInRequest
import com.gokul.dto.CheckOutRequest
import com.gokul.model.Employee
import com.gokul.service.EmployeeManager
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
        val result = employeeManager.checkIn(request.empId, request.checkInDateTime)
        return Response.ok(mapOf("message" to result)).build()
    }

    @POST
    @Path("/checkout")
    fun checkOut(@Valid request: CheckOutRequest): Response {
        val result = employeeManager.checkOut(request.empId, request.checkOutDateTime)
        return Response.ok(mapOf("message" to result)).build()
    }

    @POST
    @Path("/employee")
    fun addEmployee(employee: Employee): Response {
        val result = employeeManager.addEmployee(employee)
        return if (result.startsWith("Employee Added")) {
            Response.status(Response.Status.CREATED).entity(mapOf("message" to result)).build()
        } else {
            Response.status(Response.Status.BAD_REQUEST).entity(mapOf("error" to result)).build()
        }
    }

    @DELETE
    @Path("/employee/{empId}")
    fun deleteEmployee(@PathParam("empId") empId: String): Response {
        val success = employeeManager.deleteEmployee(empId)
        return if (success) {
            Response.ok(mapOf("message" to "Employee with id $empId deleted successfully")).build()
        } else {
            Response.status(Response.Status.NOT_FOUND).entity(mapOf("error" to "Employee with id $empId not found")).build()
        }
    }

    @GET
    @Path("/employees")
    fun getAllEmployees(): Response {
        val allEmployees = employeeManager.getEmployeesList()
        return Response.ok(mapOf("employees" to allEmployees)).build()
    }
}