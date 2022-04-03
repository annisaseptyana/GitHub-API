package com.bangkit.githubapi.helper

import androidx.recyclerview.widget.DiffUtil
import com.bangkit.githubapi.entity.Favorite

class FavoriteDiffCallback (
    private val mOldFavoriteList: List<Favorite>,
    private val mNewFavoriteList: List<Favorite>
        ): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldFavoriteList.size
    }
    override fun getNewListSize(): Int {
        return mNewFavoriteList.size
    }
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldFavoriteList[oldItemPosition].id == mNewFavoriteList[newItemPosition].id
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = mOldFavoriteList[oldItemPosition]
        val newUser = mNewFavoriteList[newItemPosition]
        return oldUser.login == newUser.login
                && oldUser.url == newUser.url
                && oldUser.avatar_url == newUser.avatar_url
    }
}