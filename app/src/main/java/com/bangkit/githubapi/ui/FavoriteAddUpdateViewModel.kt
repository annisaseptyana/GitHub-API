package com.bangkit.githubapi.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import com.bangkit.githubapi.entity.Favorite
import com.bangkit.githubapi.repository.FavoriteRepository

class FavoriteAddUpdateViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun insert(favorite: Favorite){
        mFavoriteRepository.insert(favorite)
    }

    fun update(favorite: Favorite) {
        mFavoriteRepository.update(favorite)
    }

    fun delete(favorite: Favorite) {
        mFavoriteRepository.delete(favorite)
    }
}