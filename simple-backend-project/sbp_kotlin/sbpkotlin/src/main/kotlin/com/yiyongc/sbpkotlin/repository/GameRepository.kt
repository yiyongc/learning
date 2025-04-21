package com.yiyongc.sbpkotlin.repository

import com.yiyongc.sbpkotlin.model.Game
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface GameRepository : JpaRepository<Game, UUID>