package com.intellisoft.patientvisit.data.repository.assessment

import android.util.Log
import com.intellisoft.patientvisit.data.local.dao.AssessmentDao
import com.intellisoft.patientvisit.data.local.entity.assessment.AssessmentEntity
import com.intellisoft.patientvisit.data.remote.dto.assessment.request.AssessmentRequestDto
import com.intellisoft.patientvisit.data.remote.dto.assessment.response.VisitAssessmentResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType

class VisitAssessmentRepositoryImpl(
    private val client: HttpClient,
    private val dao: AssessmentDao
): VisitAssessmentRepository {

    private val baseUrl = "https://patientvisitapis.intellisoftkenya.com/api/"

    override suspend fun saveAssessmentLocal(assessment: AssessmentEntity) {
        dao.insertAssessment(assessment)
    }

    override suspend fun saveAssessmentRemote(dto: AssessmentRequestDto): VisitAssessmentResponseDto? {
        return try {
            val response = client.post("${baseUrl}visits/add") {
                contentType(ContentType.Application.Json)
                setBody(dto)
            }
            Log.d(TAG, "📤 Sent: $dto")
            Log.d(TAG, "📥 Response: ${response.bodyAsText()}")
            response.body()
        } catch (e: Exception) {
            Log.e(TAG, "❌ Error: ${e.message}")
            null
        }
    }

    companion object{
        private const val TAG = "VisitAssessmentReposito"
    }
}