package com.gokul.resource

import com.gokul.dto.CheckInRequest
import com.gokul.dto.CheckOutRequest
import com.gokul.model.Employee
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
    fun addEmployee(employee: Employee): Response {
        val result = employeeManager.addEmployee(employee)
        return Response.ok(result).build()
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