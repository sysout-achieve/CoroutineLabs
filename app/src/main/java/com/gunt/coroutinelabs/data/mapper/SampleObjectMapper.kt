package com.gunt.coroutinelabs.data.mapper

import com.gunt.coroutinelabs.data.local.entites.SampleEntity
import com.gunt.coroutinelabs.domain.model.SampleDomainData

fun SampleEntity.toDomain(): SampleDomainData {
    return SampleDomainData(
        sampleId = this.id ?: 0,
        sampleName = this.name
    )
}