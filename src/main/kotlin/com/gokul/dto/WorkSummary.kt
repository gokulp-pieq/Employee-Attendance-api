package com.gokul.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

data class WorkSummary(
    @get:JsonProperty("empId")
    @get:NotBlank(message = "empId cannot be empty")
    val empId: String,
    val hours: Long,
    val minutes: Int
)
