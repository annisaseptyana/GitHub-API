package com.bangkit.githubapi.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkit.githubapi.entity.Favorite
import com.bangkit.githubapi.repository.FavoriteRepository

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun getAllFavorites(): LiveData<List<Favorite>> = mFavoriteRepository.getAllFavorite()
}