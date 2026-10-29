package com.example.oving7

import androidx.room3.Index
import androidx.room3.Entity
import androidx.room3.PrimaryKey

// tabell for film
@Entity(
    tableName = "movie",
    indices = [Index(value = ["title"], unique = true)]
)
data class Movie(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val directorId: Long
)