package com.yiyongc.sbpktor.routes

import com.yiyongc.sbpktor.dto.CreateGameRequest
import com.yiyongc.sbpktor.dto.CreatePublisherRequest
import com.yiyongc.sbpktor.model.toDto
import com.yiyongc.sbpktor.repository.GameRepository
import com.yiyongc.sbpktor.repository.PublisherRepository
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.*

fun Route.publisherRoutes(
    publisherRepo: PublisherRepository,
    gameRepo: GameRepository
) {
    route("/api/publishers") {
        get {
            val publishers = newSuspendedTransaction {
                publisherRepo.findAll().map { it.toDto() }
            }
            call.respond(publishers)
        }

        post {
            val req = call.receive<CreatePublisherRequest>()
            val newPublisher = publisherRepo.create(req.name).toDto()
            call.respond(HttpStatusCode.Created, newPublisher)
        }

        delete("{id}") {
            val idParam = call.parameters["id"]
            val id = runCatching { UUID.fromString(idParam) }.getOrNull()

            if (id == null) {
                call.respondText("Invalid UUID", status = HttpStatusCode.BadRequest)
                return@delete
            }

            val deleted = publisherRepo.delete(id)
            if (deleted) {
                call.respondText("Deleted", status = HttpStatusCode.OK)
            } else {
                call.respondText("Not found", status = HttpStatusCode.NotFound)
            }
        }

        post("{id}/games") {
            val publisherId =
                call.parameters["id"]?.let { runCatching { UUID.fromString(it) }.getOrNull() }

            if (publisherId == null) {
                call.respondText("Invalid UUID", status = HttpStatusCode.BadRequest)
                return@post
            }

            val req = call.receive<CreateGameRequest>()

            try {
                val newGameDto = newSuspendedTransaction {
                    val game = gameRepo.create(
                        title = req.title,
                        publishedDate = req.publishedDate,
                        inventoryCount = req.inventoryCount,
                        publisherId = publisherId
                    )
                    game.toDto()
                }
                call.respond(newGameDto)
            } catch (e: Exception) {
                call.respondText(
                    "Failed to create game: ${e.message}",
                    status = HttpStatusCode.InternalServerError
                )
            }
        }
    }
}