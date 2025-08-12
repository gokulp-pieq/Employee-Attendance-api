package com.gokul.model

enum class Department {
    SOFTWARE_DEVELOPMENT,
    QUALITY_ASSURANCE,
    IT_SUPPORT,
    NETWORK_ADMINISTRATION,
    CYBERSECURITY,
    DATABASE_ADMINISTRATION,
    SYSTEM_ADMINISTRATION,
    DEVOPS,
    CLOUD_COMPUTING,
    DATA_SCIENCE,
    PROJECT_MANAGEMENT,
    BUSINESS_ANALYSIS,
    UX_UI_DESIGN,
    TECHNICAL_WRITING;

    companion object {
        override fun toString(): String {
            return Department.entries.joinToString(", ") { it.toString() }
        }

        fun from(input: String): Department? {
            return try {
                Department.valueOf(input.trim().uppercase())
            } catch (e: IllegalArgumentException) {
                null
            }
        }
    }
}
