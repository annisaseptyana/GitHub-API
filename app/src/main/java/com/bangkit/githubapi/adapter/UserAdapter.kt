package com.bangkit.githubapi.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.githubapi.R
import com.bangkit.githubapi.database.User
import com.bangkit.githubapi.entity.Favorite
import com.bangkit.githubapi.helper.FavoriteDiffCallback
import com.bangkit.githubapi.ui.UserDetailActivity
import com.bumptech.glide.Glide

class UserAdapter(private val listUsers: ArrayList<User>) : RecyclerView.Adapter<UserAdapter.ListViewHolder>() {

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var imgAvatar: ImageView = itemView.findViewById(R.id.iv_avatar)
        var txtUsername: TextView = itemView.findViewById(R.id.tv_username)
        var txtHtmlUrl: TextView = itemView.findViewById(R.id.tv_url)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_user,parent,false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
       val (login, html_url, avatar_url) = listUsers[position]
        holder.txtUsername.text = login
        holder.txtHtmlUrl.text = html_url
        Glide.with(holder.imgAvatar.context)
            .load(avatar_url)
            .circleCrop()
            .into(holder.imgAvatar)
        holder.imgAvatar.setOnClickListener {
            val intent = Intent(holder.itemView.context, UserDetailActivity::class.java)
            intent.putExtra(UserDetailActivity.USERNAME, login)
            intent.putExtra(UserDetailActivity.URL, html_url)
            intent.putExtra(UserDetailActivity.AVATAR, avatar_url)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = listUsers.size
}