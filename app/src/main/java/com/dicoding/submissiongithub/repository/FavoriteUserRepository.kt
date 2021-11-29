package com.dicoding.submissiongithub.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.submissiongithub.database.FavoriteUserDao
import com.dicoding.submissiongithub.database.FavoriteUserDatabase
import com.dicoding.submissiongithub.response.UsersResponse
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository (application: Application) {
    // Setup variable
    private val mFavoriteUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserDatabase.getDatabase(application)
        mFavoriteUserDao = db.favoriteUserDao()
    }

    fun isFavoriteUser(username: String): LiveData<Boolean> = mFavoriteUserDao.isUserFavorite(username)

    fun getAllFavoriteUser(): LiveData<List<UsersResponse>> = mFavoriteUserDao.getAllFavoriteUser()

    fun insert(favoriteUser: UsersResponse) {
        executorService.execute { mFavoriteUserDao.insert(favoriteUser) }
    }

    fun delete(favoriteUser: UsersResponse) {
        executorService.execute { mFavoriteUserDao.delete(favoriteUser) }
    }
}