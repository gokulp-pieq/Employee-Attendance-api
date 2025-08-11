package com.gokul.model

enum class Manager {
    PIEQM1001,
    PIEQM1002,
    PIEQM1003,
    PIEQM1004;

    companion object {
        override fun toString(): String {
            return entries.joinToString(", ") { it.toString() }
        }

        fun from(input: String): Manager? {
            return try {
                valueOf(input.trim().uppercase())
            } catch (e: IllegalArgumentException) {
                null
            }
        }
    }
}