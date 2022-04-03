package com.bangkit.githubapi.database

import com.bangkit.githubapi.entity.Favorite
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite: Favorite)

    @Delete
    fun delete(favorite: Favorite)

    @Update
    fun update(favorite: Favorite)

    @Query("SELECT * FROM favorite ORDER BY id ASC")
    fun getAllUsers(): LiveData<List<Favorite>>

    @Query("SELECT * FROM favorite WHERE login = :username")
    fun getUser(username:String): LiveData<List<Favorite>>
}