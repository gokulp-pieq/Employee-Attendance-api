package com.gokul
import com.gokul.model.AttendanceList
import com.gokul.model.EmployeeList
import com.gokul.resource.AttendanceResource
import com.gokul.resource.EmployeeResource
import com.gokul.service.EmployeeManager
import io.dropwizard.Application
import io.dropwizard.setup.Environment
class Application : Application<AttendanceConfiguration>() {
    override fun run(configuration: AttendanceConfiguration, environment: Environment) {
        val employeeList= EmployeeList()
        val attendanceList= AttendanceList()
        val attendanceManager= EmployeeManager(employeeList,attendanceList)
        val attendanceResource = AttendanceResource(attendanceManager)
        val employeeResource= EmployeeResource(attendanceManager)
        environment.jersey().register(attendanceResource)
        environment.jersey().register(employeeResource)
    }
}
fun main(args: Array<String>) {
    Application().run(*args)
}