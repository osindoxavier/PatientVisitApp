package com.intellisoft.patientvisit.data.repository.vitals

import android.util.Log
import com.intellisoft.patientvisit.data.local.dao.VitalsDao
import com.intellisoft.patientvisit.data.local.entity.vital.VitalsEntity
import com.intellisoft.patientvisit.data.remote.dto.patient.response.PatientRegistrationResponseDto
import com.intellisoft.patientvisit.data.remote.dto.vital.request.VitalsRequestDto
import com.intellisoft.patientvisit.data.remote.dto.vital.response.VitalsReponseDto
import com.intellisoft.patientvisit.data.repository.patient.PatientRepositoryImpl
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType

class VitalsRepositoryImpl(
    private val client: HttpClient,
    private val dao: VitalsDao
): VitalsRepository {
    private val baseUrl = "https://patientvisitapis.intellisoftkenya.com/api/"

    override suspend fun saveVitalsLocal(vitals: VitalsEntity) {
        dao.insertVitals(vitals)
    }

    override suspend fun getLatestVitals(patientId: String): VitalsEntity? {
        return dao.getLatestVitals(patientId = patientId)
    }

    override suspend fun saveVitalsRemote(dto: VitalsRequestDto): VitalsReponseDto {
        return try {
            val response = client.post("${baseUrl}vital/add") {
                contentType(ContentType.Application.Json)
                setBody(dto)
            }
            Log.d(TAG, "📥 Raw Response: ${response.bodyAsText()}")
            val body: VitalsReponseDto = response.body()
            body
        } catch (e: Exception) {
            Log.e(TAG, "❌ ${e.message}")
            VitalsReponseDto(
                message = e.message ?: "Register User error",
                success = false,
                code = 500
            )
        }
    }

    companion object{
        private const val TAG = "VitalsRepositoryImpl"
    }

}