package com.yiyongc.sbpktor.repository

import com.yiyongc.sbpktor.model.GameEntity
import com.yiyongc.sbpktor.model.Publisher
import com.yiyongc.sbpktor.model.PublisherEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.*

class GameRepositoryImpl : GameRepository {
    override suspend fun findAll(): List<GameEntity> = newSuspendedTransaction(Dispatchers.IO) {
        GameEntity.all().toList()
    }

    override suspend fun findById(id: UUID): GameEntity? = newSuspendedTransaction(Dispatchers.IO) {
        GameEntity.findById(id)
    }

    override suspend fun create(
        title: String,
        publishedDate: Instant,
        inventoryCount: Int,
        publisherId: UUID
    ): GameEntity = newSuspendedTransaction(Dispatchers.IO) {
        val publisherEntity = PublisherEntity.findById(publisherId)
            ?: throw EntityNotFoundException(EntityID(publisherId, Publisher), PublisherEntity)

        GameEntity.new {
            this.title = title
            this.publishedDate = publishedDate
            this.inventoryCount = inventoryCount
            this.publisher = publisherEntity
            this.createdDate =
                Clock.System.now()
            this.updatedDate = Clock.System.now()
        }
    }

    override suspend fun delete(id: UUID): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        GameEntity.findById(id)?.let {
            it.delete()
            true
        } ?: false
    }
}