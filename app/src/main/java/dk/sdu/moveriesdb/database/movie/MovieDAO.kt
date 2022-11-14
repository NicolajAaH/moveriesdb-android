package dk.sdu.moveriesdb.database.movie

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MovieDAO {
    @Query("SELECT * FROM Movie")
    fun getAll(): List<Movie>

    @Query("SELECT * FROM Movie WHERE :productionId = id")
    fun loadById(productionId: Int): Movie

    @Insert
    fun insert(employee: Movie)

    @Delete
    fun delete(employee: Movie)
}