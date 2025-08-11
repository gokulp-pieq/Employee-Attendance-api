package com.gokul.model

import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class AttendanceList : ArrayList<Attendance>() {


    override fun add(attendance: Attendance): Boolean {
        return super.add(attendance)
    }

    fun hasAlreadyCheckedIn(empId: String, inputDateTime: LocalDateTime): Boolean {
        return this.any {
            it.empId == empId && it.checkInDateTime.toLocalDate() == inputDateTime.toLocalDate()
        }
    }

    fun validateCheckOut(empId: String, checkOutDateTime: LocalDateTime): Attendance? {
        return this.find {
            it.empId == empId &&
                    it.checkInDateTime.toLocalDate() == checkOutDateTime.toLocalDate() &&
                    it.checkOutDateTime == null &&
                    it.checkInDateTime.truncatedTo(ChronoUnit.MINUTES) < checkOutDateTime.truncatedTo(ChronoUnit.MINUTES)
        }
    }

    fun checkOut(attendance: Attendance,checkOutDateTime: LocalDateTime){
        attendance.checkOutDateTime=checkOutDateTime
        attendance.workingHrs=Duration.between(attendance.checkInDateTime,checkOutDateTime)
    }

    fun summaryOfWorkingHrs(startDate: LocalDate,endDate: LocalDate): List<String>{
        val ansList=mutableListOf<String>()
        if(this.isEmpty())   {
            return ansList
        }

        //Sort the filtered list based on empId
        val sortedList=this.sortedBy { it.empId }

        var lastEmployeeId=sortedList[0].empId
        var totMins: Duration= Duration.ZERO

        for(attendance in this){
            if (attendance.checkOutDateTime != null && attendance.checkInDateTime.toLocalDate() in startDate..endDate){
                if(attendance.empId==lastEmployeeId){
                    totMins+=attendance.workingHrs
                }
                else{
                    ansList.add("$lastEmployeeId  ->  ${totMins.toHours()}h ${totMins.toMinutesPart()}m")
                    lastEmployeeId=attendance.empId
                    totMins= attendance.workingHrs
                }
            }
        }
        if(totMins!= Duration.ZERO){
            ansList.add("$lastEmployeeId  ->  ${totMins.toHours()}h ${totMins.toMinutesPart()}m")
        }
        return ansList
    }

    fun delete(empId: String, date: LocalDate): Boolean {
        return this.removeIf { it.empId == empId && it.checkInDateTime.toLocalDate() == date }
    }
}
