package com.bangkit.githubapi.database

import com.bangkit.githubapi.entity.Favorite
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite: Favorite)

    @Update
    fun update(favorite: Favorite)

    @Delete
    fun delete(favorite: Favorite)

    @Query("SELECT * from favorite ORDER BY id ASC")
    fun getAllNotes(): LiveData<List<Favorite>>
}