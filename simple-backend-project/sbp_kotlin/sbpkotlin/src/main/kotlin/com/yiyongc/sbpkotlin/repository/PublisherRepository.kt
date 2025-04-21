package com.yiyongc.sbpkotlin.repository

import com.yiyongc.sbpkotlin.model.Publisher
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PublisherRepository : JpaRepository<Publisher, UUID>