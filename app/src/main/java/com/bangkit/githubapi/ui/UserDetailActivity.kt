package com.bangkit.githubapi.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bangkit.githubapi.ApiConfig
import com.bangkit.githubapi.R
import com.bangkit.githubapi.adapter.SectionsPagerAdapter
import com.bangkit.githubapi.databinding.ActivityUserDetailBinding
import com.bangkit.githubapi.entity.Favorite
import com.bangkit.githubapi.helper.ViewModelFactory
import com.bangkit.githubapi.response.UserDetailResponse
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var favorite: Favorite
    private var isExist = false

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
        const val USERNAME = "username"
        const val URL = "url"
        const val AVATAR = "avatar"
    }

    private fun getUserDetail(userDetail: String?) {
        binding.progressBar.visibility = View.VISIBLE
        val client = ApiConfig.getApiService().getDetail(userDetail.toString())
        client.enqueue(object: Callback<UserDetailResponse> {
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>) {

                val responseBody = response.body()
                if(response.isSuccessful && responseBody != null) {
                   binding.progressBar.visibility = View.INVISIBLE
                   Glide.with(this@UserDetailActivity)
                       .load(responseBody.avatarUrl)
                       .circleCrop()
                       .into(binding.ivDetailAvatar)

                   binding.apply {
                       tvDetailUsername.text = responseBody.login
                       tvDetailName.text = responseBody.name
                       tvDetailLocation.text = (responseBody.location ?: resources.getString(R.string.location)).toString()
                       tvDetailCompany.text = responseBody.company.toString()
                       tvFollowerAmount.text = responseBody.followers.toString()
                       tvFollowingAmount.text = responseBody.following.toString()
                       tvRepositoryAmount.text = responseBody.publicRepos.toString()
                   }
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

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        favoriteViewModel = obtainViewModel(this@UserDetailActivity)
        favorite = Favorite()
        setContentView(binding.root)

        supportActionBar?.title = "User's Details"
        supportActionBar?.elevation = 0f

        val userDetail = intent.getStringExtra(USERNAME)
        getUserDetail(userDetail)

        val bundle = Bundle()
        bundle.putString(USERNAME, userDetail)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, bundle)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        favoriteViewModel.getFavorite(intent.getStringExtra(USERNAME)!!).observe(this) { favoritesList ->
            if (favoritesList != null) {
                isExist = true
                favorite = favoritesList
                binding.favoriteButton.setImageResource(R.drawable.ic_baseline_favorite_24)
            }
            else {
                isExist = false
                binding.favoriteButton.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
        }

        binding.favoriteButton.setOnClickListener{
            val login = intent.getStringExtra(USERNAME)
            val url = intent.getStringExtra(URL)
            val avatar = intent.getStringExtra(AVATAR)

            favorite.let {
                favorite.login = login
                favorite.url = url
                favorite.avatar_url = avatar

                if(isExist) {
                    favoriteViewModel.delete(favorite)
                    binding.favoriteButton.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                    Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show()
                }
                else {
                    favoriteViewModel.insert(favorite)
                    binding.favoriteButton.setImageResource(R.drawable.ic_baseline_favorite_24)
                    Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}