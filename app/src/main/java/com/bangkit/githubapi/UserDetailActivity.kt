package com.bangkit.githubapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.StringRes
import com.bangkit.githubapi.databinding.ActivityUserDetailBinding
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
        const val USERNAME = "username"
    }

    private fun getUserDetail(userDetail: String?) {
        binding.progressBar.visibility = View.VISIBLE
        val client = ApiConfig.getApiService().getDetail(userDetail.toString())
        client.enqueue(object: Callback<UserDetailResponse> {
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                val responseBody = response.body()
                if(response.isSuccessful && responseBody != null) {
                   binding.progressBar.visibility = View.INVISIBLE
                   Glide.with(this@UserDetailActivity)
                       .load(responseBody.avatarUrl)
                       .circleCrop()
                       .into(binding.ivDetailAvatar)
                   binding.tvDetailUsername.text = responseBody.login
                   binding.tvDetailName.text = responseBody.name
                   binding.tvDetailLocation.text = (responseBody.location ?: resources.getString(R.string.location)).toString()
                   binding.tvDetailCompany.text = responseBody.company.toString()
                   binding.tvFollowerAmount.text = responseBody.followers.toString()
                   binding.tvFollowingAmount.text = responseBody.following.toString()
                   binding.tvRepositoryAmount.text = responseBody.publicRepos.toString()
                }
                else {
                   Log.e(this@UserDetailActivity.toString(), "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                Log.e(this@UserDetailActivity.toString(), "onFailure: ${t.message}")
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "${USERNAME}'s Profile"

        val userDetail = intent.getStringExtra(USERNAME)
        getUserDetail(userDetail)

        val bundle = Bundle()
        bundle.putString(USERNAME, userDetail)

    }
}