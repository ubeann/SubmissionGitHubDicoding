package com.dicoding.submissiongithub.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dicoding.submissiongithub.response.UsersResponse

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteUser: UsersResponse)

    @Delete
    fun delete(favoriteUser: UsersResponse)

    @Query("SELECT * from favoriteUser ORDER BY id ASC")
    fun getAllFavoriteUser(): LiveData<List<UsersResponse>>

    @Query("SELECT EXISTS (SELECT 1 FROM favoriteUser WHERE username = :username)")
    fun isUserFavorite(username: String): LiveData<Boolean>
}