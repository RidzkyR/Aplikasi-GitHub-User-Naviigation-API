package com.example.submission_fundamental_android.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission_fundamental_android.data.response.GitHubResponse
import com.example.submission_fundamental_android.data.response.ItemsItem
import com.example.submission_fundamental_android.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel :ViewModel() {
    companion object{
        private const val  TAG = "MainViewModel"
        private const val USERNAME = "Dicoding"
    }

    private val _user = MutableLiveData<List<ItemsItem>>()
    val listUser : LiveData<List<ItemsItem>> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        user(USERNAME)
    }
    fun user(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(username)
        client.enqueue(object : Callback<GitHubResponse> {
            override fun onResponse(
                call: Call<GitHubResponse>,
                response: Response<GitHubResponse>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                   _user.value = response.body()?.items
                } else {
                Log.d(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<GitHubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}