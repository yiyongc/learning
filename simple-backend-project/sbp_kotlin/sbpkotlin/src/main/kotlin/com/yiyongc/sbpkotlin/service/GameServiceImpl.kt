package com.yiyongc.sbpkotlin.service

import com.yiyongc.sbpkotlin.dto.GameDto
import com.yiyongc.sbpkotlin.dto.UpdateGameRequest
import com.yiyongc.sbpkotlin.repository.GameRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class GameServiceImpl(private val gameRepository: GameRepository) : GameService {

    override fun updateGame(id: UUID, request: UpdateGameRequest): GameDto {
        val game = gameRepository.findById(id).orElseThrow { throw EntityNotFoundException() }
        if (request.name == null && request.count == null) {
            throw IllegalArgumentException("editing name and count cannot be null or empty")
        }
        if (request.name != null) {
            game.name = request.name
        }
        if (request.count != null) {
            game.inventoryCount = request.count
        }
        return GameDto(
            game.id!!,
            game.name!!,
            game.inventoryCount,
            game.publishedDate!!,
            game.publisher!!.name
        )
    }

    override fun deleteGame(id: UUID) {
        gameRepository.deleteById(id)
    }

    override fun purchaseGame(id: UUID) {
        val game = gameRepository.findById(id).orElseThrow { throw EntityNotFoundException() }
        if (game.inventoryCount == 0) {
            throw IllegalArgumentException("purchasing game has no stock")
        }
        // do purchase
        game.inventoryCount--
    }
}