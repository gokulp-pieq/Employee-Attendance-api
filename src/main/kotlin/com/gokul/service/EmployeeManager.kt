package com.gokul.service

import com.gokul.dto.CheckInRequest
import com.gokul.dto.CheckOutRequest
import com.gokul.dto.CreateUserRequest
import com.gokul.model.Attendance
import com.gokul.dao.AttendanceList
import com.gokul.model.Employee
import com.gokul.dao.EmployeeList
import java.time.DateTimeException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import javax.ws.rs.BadRequestException
import com.gokul.dto.WorkSummary
import com.gokul.model.Department
import com.gokul.model.Manager
import com.gokul.model.Role

class EmployeeManager(private val employeeList: EmployeeList, private val attendanceList: AttendanceList) {
    companion object{private var serialNumber=1000}

    fun addEmployee(request: CreateUserRequest) : Employee {
        val role=Role.from(request.role)
        if(role==null){
            throw BadRequestException("Invalid role: ${request.role}")
        }

        val department= Department.from(request.department)
        if(department==null){
            throw BadRequestException("Invalid department: ${request.department}")
        }

        val reportingTo= Manager.from(request.reportingTo)
        if(reportingTo==null){
            throw BadRequestException("Invalid reportingTo: ${request.reportingTo}")
        }

        val employee= Employee(request.firstName,request.lastName,role,department,reportingTo,"PieQ${serialNumber++}")
        employeeList.add(employee)
        return employee
    }

    //Remove an employee
    fun deleteEmployee(empId: String){
        val result = employeeList.delete(empId)
        if (!result) {
            throw BadRequestException("User id not found. Failed to delete user")
        }
    }


    fun getEmployeesList(): List<Employee> {
        return employeeList
    }

    fun checkIn(request: CheckInRequest): Attendance {
        if (employeeList.employeeExists(request.empId)==null){
            throw BadRequestException("Employee ID ${request.empId} not found")  //Employee not found with the given id
        }

        val checkInDateTime = validateDateTime(request.checkInDateTime)

        if(attendanceList.hasAlreadyCheckedIn(request.empId,checkInDateTime)){
            throw BadRequestException("User has already checked in today")
        }
        val attendance= Attendance(request.empId, checkInDateTime)
        attendanceList.add(attendance)
        return attendance
    }

    private fun validateDateTime(inputDateTime: String): LocalDateTime{
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

        return if (inputDateTime.isEmpty()) {
            LocalDateTime.now()
        } else {
            try {
                val dateTime = LocalDateTime.parse(inputDateTime, formatter)
                if (dateTime.isAfter(LocalDateTime.now())) {  //Future date time
                    throw BadRequestException("Future time not allowed")
                } else dateTime
            } catch (e:DateTimeException) {  //Invalid format
                throw BadRequestException("Invalid date time format. Use(yyyy-MM-dd HH:mm)")
            }
        }
    }

    fun checkOut(request: CheckOutRequest): Attendance{
        if (employeeList.employeeExists(request.empId)==null){
            throw BadRequestException("Employee ID ${request.empId} not found")  //Employee not found with the given id
        }

        val checkOutDateTime= validateDateTime(request.checkOutDateTime)

        val attendance: Attendance?= attendanceList.validateCheckOut(request.empId,checkOutDateTime)
        if(attendance== null){
            throw BadRequestException("No valid check-ins yet")    //Invalid check-out
        }

        attendanceList.checkOut(attendance,checkOutDateTime)  //Valid check out
        return attendance
    }


    fun getAttendanceList(): List<Attendance>{
        return attendanceList
    }

    //Return list of Attendances, where checked in only, no checked out.
    fun getIncompleteAttendances(): List<Attendance> {
        return attendanceList.getIncompleteAttendance()
    }

    //Returns cumulative working hrs of employees between the given dates
    fun getTotalWorkingHrsBetween(startingDate: String,endingDate: String): List<WorkSummary>?{
        val startDate = parseDate(startingDate)
        if(startDate==null){
            return null
        }

        val endDate = parseDate(endingDate)
        if(endDate==null){
            return null
        }

        return attendanceList.summaryOfWorkingHrs(startDate,endDate)
    }

    fun parseDate(input: String): LocalDate? {
        val formatter= DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return try {
            LocalDate.parse(input, formatter)
        } catch (e: DateTimeParseException) {
            null
        }
    }

}
