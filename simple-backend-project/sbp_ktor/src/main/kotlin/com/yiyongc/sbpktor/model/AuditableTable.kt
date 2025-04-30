package com.yiyongc.sbpktor.model

import kotlinx.datetime.Clock
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

abstract class AuditableTable(name: String) : Table(name) {
    var createdDate = timestamp("created_date").clientDefault { Clock.System.now() }
    var updatedDate = timestamp("updated_date").clientDefault { Clock.System.now() }
}