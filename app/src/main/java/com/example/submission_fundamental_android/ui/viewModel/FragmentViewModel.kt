package com.example.submission_fundamental_android.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission_fundamental_android.data.response.ItemsItem
import com.example.submission_fundamental_android.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentViewModel : ViewModel() {
    companion object {
        private const val TAG = "FragmentViewModel"
    }

    private val _follower = MutableLiveData<List<ItemsItem>>()
    val follower: LiveData<List<ItemsItem>> = _follower

    private val _following = MutableLiveData<List<ItemsItem>>()
    val following: LiveData<List<ItemsItem>> = _following

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var isLoadFollower = false
    private var isLoadFollowing = false

    fun getFollower(username: String) {
        if (!isLoadFollower) {
            _isLoading.value = true
            val client = ApiConfig.getApiService().getFollowers(username)
            client.enqueue(object : Callback<List<ItemsItem>> {

                override fun onResponse(
                    call: Call<List<ItemsItem>>,
                    response: Response<List<ItemsItem>>,
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _follower.value = response.body()
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
            isLoadFollower = true
        }
    }

    fun getFollowing(username: String) {
        if (!isLoadFollowing) {
            _isLoading.value = true
            val client = ApiConfig.getApiService().getFollowing(username)
            client.enqueue(object : Callback<List<ItemsItem>> {
                override fun onResponse(
                    call: Call<List<ItemsItem>>,
                    response: Response<List<ItemsItem>>,
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _following.value = response.body()
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
            isLoadFollowing = true
        }
    }
}