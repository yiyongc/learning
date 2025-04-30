package com.yiyongc.sbpktor.routes

import com.yiyongc.sbpktor.dto.UpdateGameRequest
import com.yiyongc.sbpktor.model.GameEntity
import com.yiyongc.sbpktor.model.toDto
import com.yiyongc.sbpktor.repository.GameRepository
import io.ktor.http.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.datetime.Clock
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.*

fun Route.gameRoutes(
    gameRepo: GameRepository,
) {
    route("/api/games") {

        delete("{id}") {
            val gameId = call.parameters["id"]?.let { UUID.fromString(it) }

            if (gameId == null) {
                call.respondText("Invalid UUID", status = HttpStatusCode.BadRequest)
                return@delete
            }

            val deleted = gameRepo.delete(gameId)
            if (deleted) {
                call.respondText("Deleted", status = HttpStatusCode.OK)
            } else {
                call.respondText("Not found", status = HttpStatusCode.NotFound)
            }
        }

        post("{id}/purchase") {
            val gameId = call.parameters["id"]?.let { UUID.fromString(it) }

            if (gameId != null) {
                try {
                    val game = newSuspendedTransaction {
                        val gameEntity = GameEntity.findById(gameId)
                            ?: throw NotFoundException("Game not found")

                        if (gameEntity.inventoryCount > 0) {
                            gameEntity.inventoryCount -= 1
                            gameEntity.updatedDate = Clock.System.now()
                            gameEntity.toDto()
                        } else {
                            call.respond(HttpStatusCode.Conflict, "No stock left")
                            return@newSuspendedTransaction null
                        }
                    }

                    if (game != null) {
                        call.respond(game)
                    }
                } catch (e: Exception) {
                    call.respondText(
                        "Failed to purchase game: ${e.message}",
                        status = HttpStatusCode.InternalServerError
                    )
                }
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid game ID format")
            }
        }

        patch("{id}") {
            val gameId = call.parameters["id"]?.let { UUID.fromString(it) }
            val req = call.receive<UpdateGameRequest>()

            if (gameId != null) {
                try {
                    val updatedGame = newSuspendedTransaction {
                        val game = GameEntity.findById(gameId)
                            ?: throw NotFoundException("Game not found")

                        // Update fields if they are not null
                        req.title?.let { game.title = it }
                        req.inventoryCount?.let { game.inventoryCount = it }

                        game.toDto()
                    }
                    call.respond(updatedGame)
                } catch (e: Exception) {
                    call.respondText(
                        "Failed to update game: ${e.message}",
                        status = HttpStatusCode.InternalServerError
                    )
                }
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid game ID format")
            }
        }
    }
}