package com.gunt.coroutinelabs.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gunt.coroutinelabs.data.local.dao.SampleDao
import com.gunt.coroutinelabs.data.local.entites.SampleEntity

@Database(
    entities = [SampleEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    // TODO: PUT YOUR DAOs HERE
    abstract fun sampleDao(): SampleDao
}