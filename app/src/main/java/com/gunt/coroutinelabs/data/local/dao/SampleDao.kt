package com.gunt.coroutinelabs.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gunt.coroutinelabs.data.local.entites.SampleEntity

@Dao
interface SampleDao {

    @Query("SELECT * FROM SampleTable")
    fun getAllSamples(): List<SampleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllSampled(sampleData: List<SampleEntity>)
}