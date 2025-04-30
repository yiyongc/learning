package com.yiyongc.sbpktor.model

import kotlinx.datetime.Clock
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object Game : UUIDTable("game") {
    val title = varchar("title", 255)
    val publishedDate = timestamp("published_date")
    val inventoryCount = integer("inventory_count")
    val publisher = reference("publisher_id", Publisher)
    val createdDate = timestamp("created_date").clientDefault { Clock.System.now() }
    val updatedDate = timestamp("updated_date").clientDefault { Clock.System.now() }
}