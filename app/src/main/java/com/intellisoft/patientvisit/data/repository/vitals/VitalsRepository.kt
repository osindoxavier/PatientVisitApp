package com.intellisoft.patientvisit.data.repository.vitals

import com.intellisoft.patientvisit.data.local.entity.vital.VitalsEntity
import com.intellisoft.patientvisit.data.remote.dto.vital.request.VitalsRequestDto
import com.intellisoft.patientvisit.data.remote.dto.vital.response.VitalsReponseDto

interface VitalsRepository {
    suspend fun saveVitalsLocal(vitals: VitalsEntity)
    suspend fun saveVitalsRemote(dto: VitalsRequestDto): VitalsReponseDto
}