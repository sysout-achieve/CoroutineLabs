package com.gunt.coroutinelabs.di

import android.app.Application
import androidx.room.Room
import com.gunt.coroutinelabs.R
import com.gunt.coroutinelabs.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    @Singleton
    @Provides
    fun providesRoomDatabase(application: Application) = Room.databaseBuilder(
        application.applicationContext,
        AppDatabase::class.java,
        application.getString(R.string.app_name)
    ).fallbackToDestructiveMigration().build()
}
