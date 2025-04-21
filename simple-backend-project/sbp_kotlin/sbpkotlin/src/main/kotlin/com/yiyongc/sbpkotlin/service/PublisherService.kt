package com.yiyongc.sbpkotlin.service

import com.yiyongc.sbpkotlin.dto.CreatePublisherGameRequest
import com.yiyongc.sbpkotlin.dto.CreatePublisherRequest
import com.yiyongc.sbpkotlin.dto.GameDto
import com.yiyongc.sbpkotlin.dto.PublisherDto
import java.util.*

interface PublisherService {
    fun getAllPublishers(): List<PublisherDto>

    fun createPublisher(publisher: CreatePublisherRequest): PublisherDto

    fun deletePublisher(id: UUID)

    fun addGameForPublisher(id: UUID, createGameRequest: CreatePublisherGameRequest): GameDto
}