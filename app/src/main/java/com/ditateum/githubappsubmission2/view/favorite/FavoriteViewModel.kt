package com.ditateum.githubappsubmission2.view.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ditateum.githubappsubmission2.data.local.FavoriteUser
import com.ditateum.githubappsubmission2.data.local.FavoriteUserDao
import com.ditateum.githubappsubmission2.data.local.UserDatabase

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private var userDao : FavoriteUserDao?
    private var userDb : UserDatabase? = UserDatabase.getDatabase(application)

    init {
        userDao = userDb?.favoriteUserDao()
    }

    fun getFavoriteUser() : LiveData<List<FavoriteUser>>? {
        return userDao?.getFavoriteUser()
    }
}