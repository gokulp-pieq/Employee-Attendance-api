package com.gokul.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank


data class CreateUserRequest(
    @get:JsonProperty("firstName")
    @get:NotBlank(message = "firstName cannot be empty")
    val firstName: String="",
    @get:JsonProperty("lastName")
    @get:NotBlank(message = "lastName cannot be empty")
    val lastName: String="",
    val department: String="",
    val role: String="",
    val reportingTo: String=""
)
