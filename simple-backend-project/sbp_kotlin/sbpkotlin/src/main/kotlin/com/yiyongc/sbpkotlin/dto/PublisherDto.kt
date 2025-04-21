package com.yiyongc.sbpkotlin.dto

import java.util.*

data class PublisherDto (
    val id: UUID,
    val name: String,
    val games: List<PublisherGameDto>,
)