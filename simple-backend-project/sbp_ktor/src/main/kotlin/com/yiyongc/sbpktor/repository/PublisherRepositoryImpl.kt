package com.yiyongc.sbpktor.repository

import com.yiyongc.sbpktor.model.PublisherEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.datetime.Clock
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.*

class PublisherRepositoryImpl : PublisherRepository {

    override suspend fun findAll(): List<PublisherEntity> =
        newSuspendedTransaction(Dispatchers.IO) {
            PublisherEntity.all().toList()
        }

    override suspend fun findById(id: UUID): PublisherEntity? =
        newSuspendedTransaction(Dispatchers.IO) {
            PublisherEntity.findById(id)
        }

    override suspend fun create(name: String): PublisherEntity =
        newSuspendedTransaction(Dispatchers.IO) {
            PublisherEntity.new {
                this.name = name
                this.createdDate = Clock.System.now()
                this.updatedDate = Clock.System.now()
            }
        }

    override suspend fun delete(id: UUID): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        PublisherEntity.findById(id)?.let {
            it.delete()
            true
        } ?: false
    }
}
