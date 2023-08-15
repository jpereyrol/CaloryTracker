package com.jpereyrol.tracker_domain.repository

import com.jpereyrol.tracker_domain.model.TrackableFood
import com.jpereyrol.tracker_domain.model.TrackedFood
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TrackerRepository {

    suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int,
    ): Result<List<TrackableFood>>

    suspend fun insertTrackedFood(trackedFood: TrackedFood)

    suspend fun deleteTrackedFood(trackedFood: TrackedFood)

    fun getFoodsForDate(date: LocalDate): Flow<List<TrackedFood>>
}