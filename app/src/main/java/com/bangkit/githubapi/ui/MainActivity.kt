package com.bangkit.githubapi.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.githubapi.ApiConfig
import com.bangkit.githubapi.R
import com.bangkit.githubapi.adapter.UserAdapter
import com.bangkit.githubapi.database.User
import com.bangkit.githubapi.databinding.ActivityMainBinding
import com.bangkit.githubapi.response.ItemsItem
import com.bangkit.githubapi.response.UserSearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        private var USERNAME = "username"
    }

    private fun setSearchUserData(items: List<ItemsItem?>?) {

        val userList = ArrayList<User>()
        if (items != null) {
            for (item in items) {
                userList.add(
                    User(
                        item?.login,
                        item?.htmlUrl,
                        item?.avatarUrl
                    )
                )
            }
        }
        val adapter = UserAdapter(userList)
        binding.rvUsers.adapter = adapter
    }

    private fun showProgressBar(state: Boolean) {

        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    private fun findUser() {

        showProgressBar(true)
        val client = ApiConfig.getApiService().getUser(USERNAME)
        client.enqueue(object: Callback<UserSearchResponse> {

            override fun onResponse(
                call: Call<UserSearchResponse>,
                response: Response<UserSearchResponse>
            ) {
                if (response.isSuccessful) {
                    showProgressBar(false)
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setSearchUserData(responseBody.items)
                    }
                }
                else {
                    Log.e(this@MainActivity.toString(), "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserSearchResponse>, t: Throwable) {
                Log.e(this@MainActivity.toString(), "onFailure: ${t.message}")
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        supportActionBar?.title = "Search User"

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            val layoutManager = GridLayoutManager(this, 2)
            binding.rvUsers.layoutManager = layoutManager
        } else {
            val layoutManager = LinearLayoutManager(this)
            binding.rvUsers.layoutManager = layoutManager
        }

        findUser()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.actionbar_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.menu_search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                USERNAME = query
                findUser()
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_favorite -> {
                startActivity(Intent(this, FavoriteActivity::class.java))
                true
            }
            R.id.menu_setting -> {
                startActivity(Intent(this, SettingActivity::class.java))
                true
            }
            else -> true
        }
    }
}