package com.gokul.model


class EmployeeList() : ArrayList<Employee>() {

    override fun add(employee:Employee): Boolean {
        return super.add(employee)
    }

    fun delete(empId: String): Boolean {
        val employee=employeeExists(empId)   //Check whether given employee id exists
        if(employee!=null){
            this.remove(employee)
            return true
        }
        return false
    }

    fun employeeExists(empId: String): Employee? {   //Check whether employee exists with given empId
        return this.find { it.id == empId }
    }


    override fun toString(): String {
        return this.joinToString("\n")
    }
}