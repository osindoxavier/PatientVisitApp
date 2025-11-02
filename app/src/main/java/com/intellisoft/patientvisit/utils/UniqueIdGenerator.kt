package com.intellisoft.patientvisit.utils

/**
 * Generates a unique alphanumeric ID suitable for identifying a patient locally.
 * Combines current timestamp + random suffix, guaranteeing uniqueness across sessions.
 */
object UniqueIdGenerator {

    private const val SUFFIX_LENGTH = 4
    private val charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"

    fun generatePatientUniqueId(): String {
        val timestampPart = System.currentTimeMillis().toString() // e.g., 1730556809874
        val randomPart = (1..SUFFIX_LENGTH)
            .map { charset.random() }
            .joinToString("")
        return "${timestampPart.takeLast(8)}$randomPart" // shorter but still unique
    }
}