package com.yiyongc.sbpkotlin.service

import com.yiyongc.sbpkotlin.dto.GameDto
import com.yiyongc.sbpkotlin.dto.UpdateGameRequest
import java.util.*

interface GameService {
    fun updateGame(id: UUID, request: UpdateGameRequest): GameDto
    fun deleteGame(id: UUID)
    fun purchaseGame(id: UUID)
}