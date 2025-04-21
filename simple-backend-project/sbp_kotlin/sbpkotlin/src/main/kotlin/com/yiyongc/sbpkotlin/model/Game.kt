package com.yiyongc.sbpkotlin.model

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "game")
class Game(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,

    @Column(nullable = false)
    var name: String? = null,

    @Column(nullable = false)
    var publishedDate: LocalDateTime? = null,

    @Column(nullable = false)
    var inventoryCount: Int = 0,

    @Column(nullable = false)
    var createdDate: LocalDateTime? = LocalDateTime.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id", nullable = false)
    var publisher: Publisher? = null,
)