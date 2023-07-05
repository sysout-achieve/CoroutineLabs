package com.gunt.coroutinelabs.data.local.entites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SampleTable")
data class SampleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String
)
