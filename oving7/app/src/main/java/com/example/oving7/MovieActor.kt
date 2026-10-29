package com.example.oving7

import androidx.room3.Index
import androidx.room3.Entity
import androidx.room3.PrimaryKey

// tabell for å knytte sammen filmer og skuespillere
@Entity(
    tableName = "movie_actor",
    primaryKeys = ["movieId", "personId"]
)
data class MovieActor(
    val movieId: Long,
    val personId: Long
)