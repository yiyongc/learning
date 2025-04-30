package com.yiyongc.sbpktor.repository

import com.yiyongc.sbpktor.model.GameEntity
import kotlinx.datetime.Instant
import java.util.*

interface GameRepository {
    suspend fun findAll(): List<GameEntity>
    suspend fun findById(id: UUID): GameEntity?
    suspend fun create(title: String, publishedDate: Instant, inventoryCount: Int, publisherId: UUID): GameEntity
    suspend fun delete(id: UUID): Boolean
}