package com.bangkit.githubapi.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.githubapi.databinding.ItemUserBinding
import com.bangkit.githubapi.entity.Favorite
import com.bangkit.githubapi.helper.FavoriteDiffCallback
import com.bangkit.githubapi.ui.UserDetailActivity
import com.bumptech.glide.Glide

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    private val listFavorites = ArrayList<Favorite>()
    fun setListUsers(listFavorites: List<Favorite>) {
        val diffCallback = FavoriteDiffCallback(this.listFavorites, listFavorites)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavorites.clear()
        this.listFavorites.addAll(listFavorites)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavorites[position])
    }

    override fun getItemCount(): Int = listFavorites.size

    inner class FavoriteViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: Favorite) {
            with(binding) {
                tvUsername.text = favorite.login
                tvUrl.text = favorite.url
                Glide.with(this.ivAvatar)
                    .load(favorite.avatar_url)
                    .circleCrop()
                    .into(this.ivAvatar)
                ivAvatar.setOnClickListener {
                    val intent = Intent(it.context, UserDetailActivity::class.java)
                    intent.putExtra(UserDetailActivity.USERNAME, favorite.login)
                    intent.putExtra(UserDetailActivity.URL, favorite.url)
                    intent.putExtra(UserDetailActivity.AVATAR, favorite.avatar_url)
                    it.context.startActivity(intent)
                }
            }
        }
    }
}