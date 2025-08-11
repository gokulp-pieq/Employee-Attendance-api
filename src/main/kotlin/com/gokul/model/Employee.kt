package com.gokul.model

class Employee(
        private val firstName: String,
        private val lastName: String,
        private val role: Role?,
        private val reportingTo: Manager?
){
    var id: String=""
    private set  //Outside class can't set it

    companion object{
        var serialNumber=1000
    }

    fun validate(): Boolean{
        var isValid=true
        //Check whether the first name and last name are valid or not
        if(!isValidName(firstName) || !isValidName(lastName)){
            isValid=false
        }

        //Check whether the employee role is correct
        if (role == null) {
            isValid=false
        }

        //Validate the reports to
        if(reportingTo==null){
            isValid=false
        }

        if(isValid){
            id="PieQ${serialNumber++}"
        }
        return isValid
    }
    fun generateEmpId(){
        this.id="PieQ${serialNumber++}"
    }

    fun getErrorMessage(): String{
        if(!isValidName(firstName) || !isValidName(lastName)){
            return "Employee firstName or lastName can't be empty or number"
        }

        //Check whether the employee role is correct
        if (role == null) {
            return "Invalid role. Valid roles are: $Role"
        }

        //Validate the reports to
        if(reportingTo==null){
            return "Invalid reportingTo. Valid managers are $Manager"
        }

        return "No Error"
    }

    private fun isValidName(name: String): Boolean{
        return (!name.isBlank() && name.all { it.isLetter() })
    }

    override fun toString(): String {
        return buildString {
            appendLine("empID        : $id")
            appendLine("Name         : $firstName $lastName")
            appendLine("Role         : $role")
            appendLine("Reporting To : $reportingTo")
        }
    }
}
