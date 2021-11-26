package com.dicoding.submissiongithub.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submissiongithub.api.ApiConfig
import com.dicoding.submissiongithub.etc.Event
import com.dicoding.submissiongithub.response.SearchResponse
import com.dicoding.submissiongithub.response.UsersResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    // Setup variable
    private val _listUser = MutableLiveData<List<UsersResponse>>()
    var listUser: LiveData<List<UsersResponse>> = _listUser
    val isLoading = MutableLiveData<Boolean>()
    private val _notificationText = MutableLiveData<Event<String>>()
    val notificationText: LiveData<Event<String>> = _notificationText

    init {
        getListUser()
    }

    private fun getListUser() {
        isLoading.value = true
        val client = ApiConfig.getApiService().getUsers()
        client.enqueue(object : Callback<List<UsersResponse>> {
            override fun onResponse(
                call: Call<List<UsersResponse>>,
                response: Response<List<UsersResponse>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    response.body()?.let {
                        _listUser.value = it
                    }
                } else {
                    _notificationText.value = Event(response.message().toString())
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UsersResponse>>, t: Throwable) {
                isLoading.value = false
                _notificationText.value = Event(t.message.toString())
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun searchUser(query: String, foundTemplate: String, notFoundTemplate: String) {
        isLoading.value = true
        val client = ApiConfig.getApiService().searchUser(query)
        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    response.body()?.let {
                        _notificationText.value = if (it.totalCount > 0) Event(String.format(foundTemplate, it.totalCount, query)) else Event(String.format(notFoundTemplate, query))
                        _listUser.value = it.items
                    }
                } else {
                    _notificationText.value = Event(response.message().toString())
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                isLoading.value = false
                _notificationText.value = Event(t.message.toString())
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object{
        private const val TAG = "MainViewModel"
    }
}