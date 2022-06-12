package com.ditateum.githubappsubmission2.view.detailuser

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ditateum.githubappsubmission2.data.local.FavoriteUser
import com.ditateum.githubappsubmission2.data.local.FavoriteUserDao
import com.ditateum.githubappsubmission2.data.local.UserDatabase
import com.ditateum.githubappsubmission2.data.model.DetailUserResponse
import com.ditateum.githubappsubmission2.service.api.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val user = MutableLiveData<DetailUserResponse>()
    private var userDao : FavoriteUserDao?
    private var userDb : UserDatabase? = UserDatabase.getDatabase(application)

    init {
        userDao = userDb?.favoriteUserDao()
    }

    fun setUserDetail(username: String) {
        RetrofitClient.apiInstance
            .getDetailUser(username)
            .enqueue(object: Callback<DetailUserResponse>{
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                   Log.d(TAG, "onFailure: ${t.message}")
                }
            })
    }

    fun getUserDetail() : LiveData<DetailUserResponse> {
        return user
    }

    fun addToFavorite(username: String, id: Int, avatarUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoriteUser(username, id, avatarUrl)

            userDao?.addToFavoriteUser(user)
        }
    }

    fun checkUser(id: Int) = userDao?.checkCountUser(id)

    fun removeFromFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFavorite(id)
        }
    }

    companion object {
        const val TAG = "USER_DETAIL_VIEW_MODEL"
    }
}