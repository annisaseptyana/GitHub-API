package com.bangkit.githubapi.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkit.githubapi.entity.Favorite
import com.bangkit.githubapi.repository.FavoriteRepository

class FavoriteViewModel(application: Application) : ViewModel() {

    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun getAllFavorites(): LiveData<List<Favorite>> = mFavoriteRepository.getAllFavorite()

    fun getFavorite(username: String): LiveData<Favorite> = mFavoriteRepository.getFavorite(username)

    fun insert(favorite: Favorite) {
        mFavoriteRepository.insert(favorite)
    }

    fun delete(favorite: Favorite) {
        mFavoriteRepository.delete(favorite)
    }
}