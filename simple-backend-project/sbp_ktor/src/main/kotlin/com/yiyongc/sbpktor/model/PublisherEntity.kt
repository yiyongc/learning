package com.yiyongc.sbpktor.model

import com.yiyongc.sbpktor.dto.PublisherDto
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class PublisherEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<PublisherEntity>(Publisher)

    var name by Publisher.name
    val games by GameEntity referrersOn Game.publisher
    var createdDate by Publisher.createdDate
    var updatedDate by Publisher.updatedDate
}

fun PublisherEntity.toDto(): PublisherDto = PublisherDto(
    id = this.id.toString(),
    name = this.name,
    games = this.games?.map { it.toPublisherGameDto() } ?: emptyList()
)