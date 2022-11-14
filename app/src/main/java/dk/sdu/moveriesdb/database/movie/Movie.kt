package dk.sdu.moveriesdb.database.movie;

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie(
    @PrimaryKey val id: Int,
    val title: String,
    val releaseYear: Int,
    val director: String,
    val imageReference: String
    )
