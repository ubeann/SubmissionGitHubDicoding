package com.dicoding.submissiongithub.view_model

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submissiongithub.etc.Event
import com.dicoding.submissiongithub.repository.FavoriteUserRepository
import com.dicoding.submissiongithub.response.UsersResponse

class FavoriteViewModel(application: Application) : ViewModel() {
    // Setup variable
    private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)
    private val _notificationText = MutableLiveData<Event<String>>()
    val notificationText: LiveData<Event<String>> = _notificationText

    fun isFavoriteUser(username: String): LiveData<Boolean> = mFavoriteUserRepository.isFavoriteUser(username)

    fun getAllFavoriteUser(): LiveData<List<UsersResponse>> = mFavoriteUserRepository.getAllFavoriteUser()

    fun insert(favoriteUser: UsersResponse) {
        mFavoriteUserRepository.insert(favoriteUser)
        _notificationText.value = Event(String.format("Menambahkan user %s ke dalam daftar favorite", favoriteUser.login))
    }

    fun delete(favoriteUser: UsersResponse) {
        mFavoriteUserRepository.delete(favoriteUser)
        _notificationText.value = Event(String.format("Menghapus user %s dari daftar favorite", favoriteUser.login))
    }
}