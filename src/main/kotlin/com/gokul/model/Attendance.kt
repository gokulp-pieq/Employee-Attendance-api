package com.gokul.model

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime
import java.time.Duration

data class Attendance(
    val empId: String,
    @get:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    val checkInDateTime: LocalDateTime) {

    @get:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    var checkOutDateTime: LocalDateTime?=null

    var workingHrs: Duration = Duration.ZERO
}