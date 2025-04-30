package com.yiyongc.sbpktor.model

import kotlinx.datetime.Clock
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object Publisher : UUIDTable("publisher") {
    val name = varchar("name", 255)
    val createdDate = timestamp("created_date").clientDefault { Clock.System.now() }
    val updatedDate = timestamp("updated_date").clientDefault { Clock.System.now() }
}