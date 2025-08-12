package com.gokul.model

import com.gokul.dto.WorkSummary
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

    fun summaryOfWorkingHrs(startDate: LocalDate,endDate: LocalDate): List<WorkSummary>{
        val ansList=mutableListOf<WorkSummary>()
        if(this.isEmpty())   {
            return ansList
        }

        //Sort the list based on empId
        val sortedList=this.sortedBy { it.empId }

        var lastEmployeeId = sortedList[0].empId
        var totDuration = Duration.ZERO

        for (attendance in sortedList) {
            if (attendance.empId == lastEmployeeId) {
                totDuration += attendance.workingHrs
            } else {
                ansList.add(
                    WorkSummary(
                        empId = lastEmployeeId,
                        hours = totDuration.toHours(),
                        minutes = totDuration.toMinutesPart()
                    )
                )
                lastEmployeeId = attendance.empId
                totDuration = attendance.workingHrs
            }
        }

        // Add last employee total
        if (totDuration != Duration.ZERO) {
            ansList.add(
                WorkSummary(
                    empId = lastEmployeeId,
                    hours = totDuration.toHours(),
                    minutes = totDuration.toMinutesPart()
                )
            )
        }
        return ansList
    }

    fun delete(empId: String, date: LocalDate): Boolean {
        return this.removeIf { it.empId == empId && it.checkInDateTime.toLocalDate() == date }
    }
}
