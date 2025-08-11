package com.gokul.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.validator.constraints.NotBlank
import java.time.LocalDateTime


data class CheckInRequest(
    @get:JsonProperty("empId")
    @get:NotBlank(message = "empId cannot be blank")
    val empId: String,
    val checkInDateTime: String
)
