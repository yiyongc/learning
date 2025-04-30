package com.yiyongc.sbpktor.config

import com.yiyongc.sbpktor.model.Game
import com.yiyongc.sbpktor.model.Publisher
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabases() {
    val db = connectToPostgres()

    environment.log.info("Connected to PostgreSQL and initializing schema...")

    transaction(db) {
        SchemaUtils.create(Publisher, Game)
    }
}

fun Application.connectToPostgres(): Database {
    val url = environment.config.property("postgres.url").getString()
    val user = environment.config.property("postgres.user").getString()
    val password = environment.config.property("postgres.password").getString()

    return Database.connect(
        url = url,
        driver = "org.postgresql.Driver",
        user = user,
        password = password
    )
}
