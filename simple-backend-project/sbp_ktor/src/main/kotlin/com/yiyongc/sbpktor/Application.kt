package com.yiyongc.sbpktor

import com.yiyongc.sbpktor.config.configureDatabases
import com.yiyongc.sbpktor.repository.GameRepositoryImpl
import com.yiyongc.sbpktor.repository.PublisherRepositoryImpl
import com.yiyongc.sbpktor.routes.publisherRoutes
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureDatabases()
    val publisherRepo = PublisherRepositoryImpl()
    val gameRepo = GameRepositoryImpl()

    install(ContentNegotiation) {
        json()
    }

    routing {
        publisherRoutes(publisherRepo, gameRepo)
    }
}
