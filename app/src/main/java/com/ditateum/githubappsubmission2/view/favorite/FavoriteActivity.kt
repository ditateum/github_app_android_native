package com.ditateum.githubappsubmission2.view.favorite

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ditateum.githubappsubmission2.data.local.FavoriteUser
import com.ditateum.githubappsubmission2.data.model.User
import com.ditateum.githubappsubmission2.databinding.ActivityFavoriteBinding
import com.ditateum.githubappsubmission2.view.adapter.UserAdapter
import com.ditateum.githubappsubmission2.view.detailuser.DetailUserActivity

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFavoriteBinding
    private lateinit var adapter: UserAdapter
    private val viewModel by viewModels<FavoriteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Favorite User"
        val colorDrawable = ColorDrawable(Color.parseColor("#ED000B1E"))
        supportActionBar?.setBackgroundDrawable(colorDrawable)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object: UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                val intentDetailUser = Intent(this@FavoriteActivity, DetailUserActivity::class.java)
                intentDetailUser.putExtra(DetailUserActivity.EXTRA_USERNAME, data.username)
                intentDetailUser.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                intentDetailUser.putExtra(DetailUserActivity.EXTRA_URL, data.avatar_url)
                startActivity(intentDetailUser)
            }
        })

        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUser.adapter = adapter
        }

        viewModel.getFavoriteUser()?.observe(this, {
            if (it != null) {
                val list = mappingList(it)
                adapter.setList(list)
            }
        })

    }

    private fun mappingList(users: List<FavoriteUser>): ArrayList<User> {
        val listUser = ArrayList<User>()
        for (user in users) {
            val userMapped = User(
                user.id,
                user.login,
                user.avatar_url,
            )
            listUser.add(userMapped)
        }
        return listUser
    }
}