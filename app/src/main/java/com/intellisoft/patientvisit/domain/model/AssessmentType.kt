package com.intellisoft.patientvisit.domain.model

/**
 * Enum defining available assessment types.
 * Used to control validation and behavior dynamically.
 */
enum class AssessmentType(val label: String) {
    GENERAL("General Assessment"),
    OVERWEIGHT("Overweight Assessment")
}