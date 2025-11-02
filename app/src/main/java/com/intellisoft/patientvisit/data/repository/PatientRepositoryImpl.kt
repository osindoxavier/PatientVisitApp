package com.intellisoft.patientvisit.data.repository

import android.util.Log
import androidx.compose.ui.autofill.ContentType
import com.intellisoft.patientvisit.data.local.dao.PatientDao
import com.intellisoft.patientvisit.data.local.entity.PatientEntity
import com.intellisoft.patientvisit.data.remote.dto.PatientRegistrationDto
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.utils.EmptyContent.contentType
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

//    private val baseUrl = BuildConfig.BASE_URL

    override suspend fun registerPatientLocal(patient: PatientEntity) {
        dao.insertPatient(patient)
    }

    override suspend fun getAllPatients(): List<PatientEntity> = dao.getAllPatients()

    override suspend fun getPatientById(patientId: String): PatientEntity? =
        dao.getPatientById(patientId)

    override suspend fun registerPatientRemote(dto: PatientRegistrationDto): Boolean {
        return try {
//            val response = client.post("${baseUrl}patients/register") {
//                contentType(ContentType.Application.Json)
//                setBody(dto)
//            }

//            val success = response.status == HttpStatusCode.OK
//            Log.d(TAG, "registerPatientRemote: -> success=$success, status=${response.status}")
//            success
            true
        } catch (e: Exception) {
            Log.e(TAG, "registerPatientRemote: -> ${e.message}", )
            false
        }
    }


    companion object{
        private const val TAG = "PatientRepositoryImpl"
    }
}