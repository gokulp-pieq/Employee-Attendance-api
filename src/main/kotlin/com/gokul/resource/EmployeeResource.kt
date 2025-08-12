package com.gokul.resource

import com.gokul.dto.CreateUserRequest
import com.gokul.service.EmployeeManager
import javax.validation.Valid
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/employee")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class EmployeeResource(
    private val employeeManager: EmployeeManager
) {
    @POST
    fun addEmployee(@Valid createUserRequest: CreateUserRequest): Response {
        val result = employeeManager.addEmployee(createUserRequest)
        return Response.ok(result).build()
    }

    @DELETE
    @Path("/{empId}")
    fun deleteEmployee(@PathParam("empId") empId: String): Response {
        employeeManager.deleteEmployee(empId)
        return Response.noContent().build()
    }

    @GET
    @Path("/all")
    fun getAllEmployees(): Response {
        val employees = employeeManager.getEmployeesList()
        return Response.ok(employees).build()
    }
}