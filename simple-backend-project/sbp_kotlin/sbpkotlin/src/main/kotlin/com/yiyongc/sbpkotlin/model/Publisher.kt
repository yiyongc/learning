package com.yiyongc.sbpkotlin.model

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "publisher")
class Publisher (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,

    @Column(nullable = false, unique = true)
    var name: String,

    @Column(nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @OneToMany(
        mappedBy = "publisher",
        cascade = [(CascadeType.ALL)],
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    var games: MutableList<Game> = mutableListOf(),
)