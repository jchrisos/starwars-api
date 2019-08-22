package com.starwars.models

import com.fasterxml.jackson.annotation.JsonFormat
import org.joda.time.LocalDateTime
import java.util.*

data class Planet(
        var id: Long?,
        val name: String,
        val climate: String,
        val terrain: String,
        var appearanceFilms: Int?,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
        var date: Date?
)