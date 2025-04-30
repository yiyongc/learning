package com.yiyongc.sbpktor.model

import com.yiyongc.sbpktor.dto.GameDto
import com.yiyongc.sbpktor.dto.PublisherDto
import com.yiyongc.sbpktor.dto.PublisherGameDto
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.UUID

class GameEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<GameEntity>(Game)

    var title by Game.title
    var publishedDate by Game.publishedDate
    var inventoryCount by Game.inventoryCount
    var publisher by PublisherEntity referencedOn Game.publisher
    var createdDate by Game.createdDate
    var updatedDate by Game.updatedDate
}

fun GameEntity.toPublisherGameDto(): PublisherGameDto = PublisherGameDto(
    id = this.id.toString(),
    title = this.title,
    publishedDate = this.publishedDate,
)

fun GameEntity.toDto(): GameDto = GameDto(
    id = this.id.toString(),
    title = this.title,
    publishedDate = this.publishedDate,
    inventoryCount = this.inventoryCount,
    publisher = this.publisher.name,
)