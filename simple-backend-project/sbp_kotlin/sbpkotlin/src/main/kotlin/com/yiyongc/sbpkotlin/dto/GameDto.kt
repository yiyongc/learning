package com.yiyongc.sbpkotlin.dto

import java.time.LocalDateTime
import java.util.UUID

data class GameDto(
    val id: UUID,
    val name: String,
    val count: Int,
    val publishedDate: LocalDateTime,
    val publisher: String,
)