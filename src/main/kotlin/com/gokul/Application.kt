package com.gokul
import com.gokul.model.AttendanceList
import com.gokul.model.EmployeeList
import com.gokul.resource.AttendanceResource
import com.gokul.service.EmployeeManager
import io.dropwizard.Application
import io.dropwizard.setup.Environment
class Application : Application<AttendanceConfiguration>() {
    override fun run(configuration: AttendanceConfiguration, environment: Environment) {
        val employeeList= EmployeeList()
        val attendanceList= AttendanceList()
        val attendanceManager= EmployeeManager(employeeList,attendanceList)
        val resource = AttendanceResource(attendanceManager)
        environment.jersey().register(resource)
    }
}
fun main(args: Array<String>) {
    Application().run(*args)
}