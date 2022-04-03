package com.bangkit.githubapi.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.bangkit.githubapi.database.FavoriteDao
import com.bangkit.githubapi.database.FavoriteRoomDatabase
import com.bangkit.githubapi.entity.Favorite
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {

    private val mFavoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteDao()
    }

    fun getAllFavorite(): LiveData<List<Favorite>> = mFavoriteDao.getAllUsers()

    fun getFavorite(username: String): LiveData<Favorite> = mFavoriteDao.getUser(username)

    fun insert(favorite: Favorite) {
        executorService.execute { mFavoriteDao.insert(favorite) }
    }

    fun delete(favorite: Favorite) {
        executorService.execute { mFavoriteDao.delete(favorite) }
    }
}