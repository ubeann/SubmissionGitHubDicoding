package com.dicoding.submissiongithub

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    // Setup variable
    private val _listUser = MutableLiveData<List<UsersResponse>>()
//    var listUser: LiveData<List<UsersResponse>> = _listUser
    private val _listDetailUser = MutableLiveData<List<DetailUserResponse?>>()
    val listDetailUser: LiveData<List<DetailUserResponse?>> = _listDetailUser
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        findListUser()
//        findDetailUser(listUser)
//        _isLoading.value = false
    }

    private fun findListUser() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsers()
        client.enqueue(object : Callback<List<UsersResponse>> {
            override fun onResponse(
                call: Call<List<UsersResponse>>,
                response: Response<List<UsersResponse>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val result = response.body()
                    result?.let {
                        _listUser.value = it
                        findDetailUser(it)
                        _isLoading.value = false
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UsersResponse>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun findDetailUser(listUser: List<UsersResponse>?) {
        val listDetailUserResult = ArrayList<DetailUserResponse?>()
        listUser?.forEach { user ->
            val client = ApiConfig.getApiService().getDetailUser(user.login)
            client.enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val result = response.body()
                        result?.let {
                            listDetailUserResult.add(it)
                        }
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message}")
                }
            })
        }
        _listDetailUser.value = listDetailUserResult
    }

    companion object{
        private const val TAG = "MainViewModel"
    }
}