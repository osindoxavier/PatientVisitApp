package com.intellisoft.patientvisit.data.repository.patient

import android.util.Log
import com.intellisoft.patientvisit.data.local.dao.PatientDao
import com.intellisoft.patientvisit.data.local.entity.patient.PatientEntity
import com.intellisoft.patientvisit.data.remote.dto.auth.response.LoginResponseDto
import com.intellisoft.patientvisit.data.remote.dto.patient.request.PatientRegistrationDto
import com.intellisoft.patientvisit.data.remote.dto.patient.response.PatientRegistrationResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType

/**
 * Handles actual Room + API data operations for patients.
 * Combines offline persistence with network synchronization.
 */
class PatientRepositoryImpl(
    private val dao: PatientDao,
    private val client: HttpClient
) : PatientRepository {

    private val baseUrl = "https://patientvisitapis.intellisoftkenya.com/api/"

    override suspend fun registerPatientLocal(patient: PatientEntity) {
        dao.insertPatient(patient)
    }

    override suspend fun getAllPatients(): List<PatientEntity> = dao.getAllPatients()

    override suspend fun getPatientById(patientId: String): PatientEntity? =
        dao.getPatientById(patientId)

    override suspend fun registerPatientRemote(dto: PatientRegistrationDto): PatientRegistrationResponseDto {
        return try {
            val response = client.post("${baseUrl}patients/register") {
                contentType(ContentType.Application.Json)
                setBody(dto)
            }
            Log.d(TAG, "📥 Response: ${response.bodyAsText()}")
            val body: PatientRegistrationResponseDto = response.body()
            body
        } catch (e: Exception) {
            Log.e(TAG, "❌ ${e.message}")
            PatientRegistrationResponseDto(
                message = e.message ?: "Register User error",
                success = false,
                code = 500
            )
        }
    }


    companion object {
        private const val TAG = "PatientRepositoryImpl"
    }
}