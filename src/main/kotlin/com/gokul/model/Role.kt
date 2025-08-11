package com.gokul.model


enum class Role {
    DEVELOPER,
    DESIGNER,
    INTERN,
    MANAGER;

    companion object {
        override fun toString(): String {
            return entries.joinToString(", ") { it.toString() }
        }

        fun from(input: String): Role? {
            return try {
                valueOf(input.trim().uppercase())
            } catch (e: IllegalArgumentException) {
                null
            }
        }
    }
}
