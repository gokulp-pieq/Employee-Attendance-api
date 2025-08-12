package com.gokul.model

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

data class Employee(
    @get:JsonProperty("firstName")
    @get:NotBlank(message = "firstName cannot be blank")
    val firstName: String,
    @get:JsonProperty("lastName")
    @get:NotBlank(message = "lastName cannot be blank")
    val lastName: String,
    val role: Role,
    val department: Department,
    val reportingTo: Manager,
    var id: String

)
