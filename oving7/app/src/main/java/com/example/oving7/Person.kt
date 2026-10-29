package com.example.oving7

import androidx.room3.Entity
import androidx.room3.Index
import androidx.room3.PrimaryKey

// tabell for person(både direktør og skuespillere)
@Entity(
    tableName = "person",
    indices = [Index(value = ["name"], unique = true)]
)
data class Person(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String
)