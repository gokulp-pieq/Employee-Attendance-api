package com.gokul.service

import com.gokul.dto.CheckInRequest
import com.gokul.dto.CheckOutRequest
import com.gokul.model.Attendance
import com.gokul.model.AttendanceList
import com.gokul.model.Employee
import com.gokul.model.EmployeeList
import java.time.DateTimeException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import javax.ws.rs.BadRequestException

class EmployeeManager(private val employeeList: EmployeeList,private val attendanceList: AttendanceList) {

//    init {
//        addInitialEmployees()
//    }
//
//    fun addInitialEmployees() {
//        employeeList.addAll(
//            listOf(
//                Employee("Gokul", "P", Role.DEVELOPER, Manager.PIEQM1001),
//                Employee( "Mark", "Lee", Role.DEVELOPER, Manager.PIEQM1001),
//                Employee( "Jack", "Lee", Role.INTERN, Manager.PIEQM1003),
//                Employee( "Ashok", "Kumar", Role.DESIGNER, Manager.PIEQM1002),
//                Employee( "Bob", "John", Role.DESIGNER,Manager.PIEQM1003),
//                Employee("Tim", "David", Role.INTERN, Manager.PIEQM1003)
//            )
//        )
//        employeeList.forEach { it.generateEmpId() }
//    }

    //Add new Employee
    fun addEmployee(employee: Employee) :String{
        //Validate the employee object and add it to the list if it is valid
        if(employee.validate()){
            employeeList.add(employee)
            return "Employee Added SuccessFully!\n$employee"
        }
        return employee.getErrorMessage()+". Failed to add an employee"
    }

    //Remove an employee
    fun deleteEmployee(empId: String): Boolean{
        return employeeList.delete(empId)
    }


    fun getEmployeesList(): String {
        return employeeList.toString()
    }

    fun checkIn(request: CheckInRequest): Attendance {
        if (employeeList.employeeExists(request.empId)==null){
            throw BadRequestException("Employee ID ${request.empId} not found")  //Employee not found with the given id
        }

        val checkInDateTime = validateDateTime(request.checkInDateTime)

        if(attendanceList.hasAlreadyCheckedIn(request.empId,checkInDateTime)){
            throw BadRequestException("User already checked in today")
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

    fun deleteAttendanceEntry(empId: String,date: String): String{
        val parsedDate=parseDate(date)
        if(parsedDate==null)  {
            return "Invalid date. Failed to delete."
        }
        if(attendanceList.delete(empId,parsedDate)){
            return "Deletion Successful!"
        }
        return "No Attendance entry find with the give details. Failed to delete"
    }

    fun getAttendanceList(): String{
        return attendanceList.toString()
    }

    //Return list of Attendances, where checked in only, no checked out.
    fun getIncompleteAttendances(): List<Attendance> {
        return attendanceList.filter { it.checkOutDateTime == null }
    }

    //Returns cumulative working hrs of employees between the given dates
    fun getTotalWorkingHrsBetween(startingDate: String,endingDate: String): List<String>?{
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
        val formatter= DateTimeFormatter.ofPattern("dd-MM-yyyy")
        return try {
            LocalDate.parse(input, formatter)
        } catch (e: DateTimeParseException) {
            null
        }
    }

}
