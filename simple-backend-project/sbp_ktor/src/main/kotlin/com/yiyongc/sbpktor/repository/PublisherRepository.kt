package com.yiyongc.sbpktor.repository

import com.yiyongc.sbpktor.model.PublisherEntity
import java.util.*

interface PublisherRepository {
    suspend fun findAll(): List<PublisherEntity>
    suspend fun findById(id: UUID): PublisherEntity?
    suspend fun create(name: String): PublisherEntity
    suspend fun delete(id: UUID): Boolean
}