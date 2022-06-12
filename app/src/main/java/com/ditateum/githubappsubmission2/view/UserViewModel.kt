package com.ditateum.githubappsubmission2.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ditateum.githubappsubmission2.data.model.User
import com.ditateum.githubappsubmission2.data.model.UserResponse
import com.ditateum.githubappsubmission2.service.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {
    private val listUsers = MutableLiveData<ArrayList<User>>()

    fun setSearchUsers(query: String) {
        RetrofitClient.apiInstance
            .getSearchUser(query)
            .enqueue(object: Callback<UserResponse>{
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        listUsers.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message}")
                }

            })
    }

    fun getSearchUsers() : LiveData<ArrayList<User>> {
        return listUsers
    }

    companion object {
        const val TAG = "USER_VIEW_MODEL"
    }

}