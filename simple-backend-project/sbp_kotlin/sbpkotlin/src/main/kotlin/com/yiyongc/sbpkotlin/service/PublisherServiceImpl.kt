package com.yiyongc.sbpkotlin.service

import com.yiyongc.sbpkotlin.dto.*
import com.yiyongc.sbpkotlin.model.Game
import com.yiyongc.sbpkotlin.model.Publisher
import com.yiyongc.sbpkotlin.repository.GameRepository
import com.yiyongc.sbpkotlin.repository.PublisherRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Service
@Transactional
class PublisherServiceImpl(
    private val publisherRepository: PublisherRepository,
    private val gameRepository: GameRepository
) :
    PublisherService {

    override fun getAllPublishers(): List<PublisherDto> {
        val publishers = publisherRepository.findAll()
        return publishers.map { p ->
            val games = p.games.map { g -> PublisherGameDto(g.id!!, g.name!!, g.publishedDate!!) }
            PublisherDto(p.id!!, p.name, games)
        }
    }

    override fun createPublisher(publisher: CreatePublisherRequest): PublisherDto {
        val games = publisher.games?.map { g ->
            Game(
                name = g.name,
                publishedDate = g.publishedDate,
                inventoryCount = g.inventoryCount
            )
        }?.toMutableList() ?: mutableListOf()

        val pb = Publisher(
            name = publisher.name,
            games = games
        )
        games.forEach { game ->
            game.publisher = pb
        }

        val savedPublisher = publisherRepository.saveAndFlush(pb)
        val pbGames = savedPublisher.games.map { g ->
            PublisherGameDto(g.id!!, g.name!!, g.publishedDate!!)
        }
        return PublisherDto(savedPublisher.id!!, savedPublisher.name, pbGames)
    }

    override fun deletePublisher(id: UUID) {
        // The cascade relationship will ensure that all games are deleted automatically
        publisherRepository.deleteById(id)
    }

    override fun addGameForPublisher(
        id: UUID,
        createGameRequest: CreatePublisherGameRequest
    ): GameDto {
        val publisher = publisherRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Publisher with ID $id not found") }
        val game = gameRepository.save(
            Game(
                name = createGameRequest.name,
                publishedDate = createGameRequest.publishedDate,
                inventoryCount = createGameRequest.inventoryCount,
                publisher = publisher
            )
        )
        return GameDto(
            game.id!!,
            game.name!!,
            game.inventoryCount,
            game.publishedDate!!,
            publisher.name
        )
    }
}