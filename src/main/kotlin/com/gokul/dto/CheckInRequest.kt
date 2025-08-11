package com.gokul.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank


data class CheckInRequest(
    @get:JsonProperty("empId")
    @get:NotBlank(message = "empId cannot be empty")
    val empId: String="",
    val checkInDateTime: String=""
)
