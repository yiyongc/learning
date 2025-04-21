package com.yiyongc.sbpkotlin.controller

import com.yiyongc.sbpkotlin.dto.*
import com.yiyongc.sbpkotlin.service.GameService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@RestController
@RequestMapping("/api/games")
class GameController(private val gameService: GameService) {

    @PatchMapping("/{id}")
    fun updateGame(@PathVariable id: UUID, @RequestBody updateRequest: UpdateGameRequest): GameDto {
        return gameService.updateGame(id, updateRequest)
    }

    @DeleteMapping("/{id}")
    fun deleteGame(@PathVariable id: UUID): ResponseEntity<Void> {
        gameService.deleteGame(id)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/{id}/purchase")
    fun purchaseGame(@PathVariable id: UUID): ResponseEntity<ApiResponse<Any>> {
        try {
            gameService.purchaseGame(id)
            return ResponseEntity.ok(ApiResponse(SuccessData(action = "purchase")))
        } catch (e: IllegalArgumentException) {
            return ResponseEntity.badRequest()
                .body(ApiResponse(ErrorData("No stocks left")))
        } catch (e: Exception) {
            return ResponseEntity.badRequest()
                .body(ApiResponse(ErrorData("Failed to purchase game")))
        }
    }
}