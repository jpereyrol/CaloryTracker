package com.jpereyrol.tracker_data.di

import android.app.Application
import androidx.room.Room
import com.jpereyrol.tracker_data.local.TrackerDatabase
import com.jpereyrol.tracker_data.remote.OpenFoodApi
import com.jpereyrol.tracker_data.repository.TrackerRepositoryImpl
import com.jpereyrol.tracker_domain.repository.TrackerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TrackerDataModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        ).build()
    }

    @Provides
    @Singleton
    fun provideOpenFoodApi(okHttpClient: OkHttpClient ): OpenFoodApi {
        return Retrofit.Builder()
            .baseUrl(OpenFoodApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideTrackerDatabase(app: Application): TrackerDatabase {
        return Room.databaseBuilder(
            app,
            TrackerDatabase::class.java,
            "tracker_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTrackerRepository(openFoodApi: OpenFoodApi, database: TrackerDatabase): TrackerRepository {
        return TrackerRepositoryImpl(database.trackerDao, openFoodApi)
    }
}