package com.yiyongc.sbpkotlin.controller

import com.yiyongc.sbpkotlin.dto.CreatePublisherGameRequest
import com.yiyongc.sbpkotlin.dto.CreatePublisherRequest
import com.yiyongc.sbpkotlin.dto.GameDto
import com.yiyongc.sbpkotlin.dto.PublisherDto
import com.yiyongc.sbpkotlin.service.PublisherService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/publishers")
class PublisherController(private val publisherService: PublisherService) {

    @GetMapping
    fun getPublishers(): List<PublisherDto> {
        return publisherService.getAllPublishers()
    }

    @PostMapping
    fun createPublisher(@RequestBody publisher: CreatePublisherRequest): PublisherDto {
        return publisherService.createPublisher(publisher)
    }

    @DeleteMapping("/{id}")
    fun deletePublisher(@PathVariable id: UUID): ResponseEntity<Void> {
        publisherService.deletePublisher(id)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/{id}/games")
    fun addGameForPublisher(
        @PathVariable id: UUID,
        @RequestBody createGameRequest: CreatePublisherGameRequest
    ): GameDto {
        return publisherService.addGameForPublisher(id, createGameRequest)
    }
}