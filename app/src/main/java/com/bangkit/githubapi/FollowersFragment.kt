package com.bangkit.githubapi

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.githubapi.databinding.FragmentFollowersBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersFragment : Fragment() {
    private lateinit var binding: FragmentFollowersBinding

    companion object {
        private var USERNAME = "username"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    private fun getFollowersData(responseBody: List<UserFollowersResponse>) {
        val userList = ArrayList<User>()

        for (item in responseBody) {
            userList.add(
                User(
                    item.login,
                    item.htmlUrl,
                    item.avatarUrl
                )
            )
        }
        val adapter = UserAdapter(userList)

        binding.apply {
            listFollowers.setHasFixedSize(true)
            if (activity?.resources?.configuration?.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                val layoutManager = GridLayoutManager(activity, 2)
                listFollowers.layoutManager = layoutManager
            } else {
                val layoutManager = LinearLayoutManager(activity)
                listFollowers.layoutManager = layoutManager
            }
            listFollowers.adapter = adapter
        }
    }

    private fun getFollowers(username: String) {
        showProgressbar(true)
        val client = ApiConfig.getApiService().getFollowersList(username)
        client.enqueue(object : Callback<List<UserFollowersResponse>> {
            override fun onResponse(
                call: Call<List<UserFollowersResponse>>,
                response: Response<List<UserFollowersResponse>>
            ) {
                if (response.isSuccessful) {
                    showProgressbar(false)
                    val responseBody = response.body()
                    if (responseBody != null) {
                        getFollowersData(responseBody)
                    }
                }
            }

            override fun onFailure(call: Call<List<UserFollowersResponse>>, t: Throwable) {
                Log.d("Failure", t.message, t)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = arguments?.getString(USERNAME).toString()
        binding = FragmentFollowersBinding.bind(view)

        getFollowers(username)
    }

    private fun showProgressbar(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }
}