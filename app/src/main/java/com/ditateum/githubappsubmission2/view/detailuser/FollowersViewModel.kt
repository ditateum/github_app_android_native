package com.ditateum.githubappsubmission2.view.detailuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ditateum.githubappsubmission2.data.model.User
import com.ditateum.githubappsubmission2.service.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {

    private val listFollowers = MutableLiveData<ArrayList<User>>()

    fun setListFollowers(username: String) {
        RetrofitClient.apiInstance
            .getFollowers(username)
            .enqueue(object: Callback<ArrayList<User>>{
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful) {
                        listFollowers.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message}")
                }

            })
    }

    fun getListFollowers() : LiveData<ArrayList<User>> {
        return listFollowers
    }


    companion object {
        const val TAG = "FOLLOWERS_VIEW_MODEL"
    }
}