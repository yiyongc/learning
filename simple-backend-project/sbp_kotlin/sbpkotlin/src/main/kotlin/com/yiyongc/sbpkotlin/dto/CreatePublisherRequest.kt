package com.yiyongc.sbpkotlin.dto

data class CreatePublisherRequest (
    val name: String,
    val games: List<CreatePublisherGameRequest>?,
)