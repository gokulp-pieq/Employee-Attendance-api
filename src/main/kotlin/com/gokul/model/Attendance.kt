package com.gokul.model

import java.time.LocalDateTime
import java.time.Duration

class Attendance(
    val empId: String,
    val checkInDateTime: LocalDateTime,
){

    var checkOutDateTime: LocalDateTime? = null
        private set
    var workingHrs: Duration= Duration.ZERO
        private set

    fun checkOut(checkOutDateTime: LocalDateTime){
        this.checkOutDateTime=checkOutDateTime
        this.workingHrs=Duration.between(this.checkInDateTime,this.checkOutDateTime)
    }
}
