package com.example.oving7

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy

@Dao
interface LibraryDao {

    // ignore gjør at rad med samme tittel ikke settes inn på nytt

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movie: Movie): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPerson(person: Person): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovieActor(movieActor: MovieActor): Long
}

