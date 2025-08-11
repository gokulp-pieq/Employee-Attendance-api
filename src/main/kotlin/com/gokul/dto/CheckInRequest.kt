package com.gokul.dto

import org.hibernate.validator.constraints.NotBlank
import javax.validation.constraints.NotNull


data class CheckInRequest(
    @field:NotNull(message="Employee Id cannot be empty")
    @field:NotBlank(message = "Employee Id cannot be null")
    val empId: String="",

    val checkInDateTime: String=""
)
