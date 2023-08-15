package com.jpereyrol.tracker_data.repository

import com.jpereyrol.tracker_data.local.TrackerDao
import com.jpereyrol.tracker_data.mapper.toTrackableFood
import com.jpereyrol.tracker_data.mapper.toTrackedFood
import com.jpereyrol.tracker_data.mapper.toTrackerFoodEntity
import com.jpereyrol.tracker_data.remote.OpenFoodApi
import com.jpereyrol.tracker_domain.model.TrackableFood
import com.jpereyrol.tracker_domain.model.TrackedFood
import com.jpereyrol.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import kotlin.Exception

class TrackerRepositoryImpl(
    private val dao: TrackerDao,
    private val api: OpenFoodApi
): TrackerRepository {

    override suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int,
    ): Result<List<TrackableFood>> {
        return try {
            val searchDto = api.searchFood(
                query = query,
                page = page,
                pageSize = pageSize
            )
            Result.success(searchDto.products.mapNotNull { it.toTrackableFood() })
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun insertTrackedFood(trackedFood: TrackedFood) {
        dao.insertTrackedFood(trackedFood.toTrackerFoodEntity())
    }

    override suspend fun deleteTrackedFood(trackedFood: TrackedFood) {
        dao.deleteTrackedFood(trackedFood.toTrackerFoodEntity())
    }

    override fun getFoodsForDate(date: LocalDate): Flow<List<TrackedFood>> {
        return dao.getFoodForDate(
            day = date.dayOfMonth,
            month = date.monthValue,
            year = date.year
        ).map { entities ->
            entities.map { it.toTrackedFood() }
        }
    }
}