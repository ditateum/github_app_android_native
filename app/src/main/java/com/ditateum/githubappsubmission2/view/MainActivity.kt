package com.ditateum.githubappsubmission2.view

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ditateum.githubappsubmission2.R
import com.ditateum.githubappsubmission2.databinding.ActivityMainBinding
import com.ditateum.githubappsubmission2.data.model.User
import com.ditateum.githubappsubmission2.view.adapter.UserAdapter
import com.ditateum.githubappsubmission2.view.darkmode.DarkModeActivity
import com.ditateum.githubappsubmission2.view.detailuser.DetailUserActivity
import com.ditateum.githubappsubmission2.view.favorite.FavoriteActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: UserAdapter
    private val viewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Github App"
        val colorDrawable = ColorDrawable(Color.parseColor("#ED000B1E"))
        supportActionBar?.setBackgroundDrawable(colorDrawable)

        userAdapter = UserAdapter()
        userAdapter.notifyDataSetChanged()



        userAdapter.setOnItemClickCallback(object: UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                val intentDetailUser = Intent(this@MainActivity, DetailUserActivity::class.java)
                intentDetailUser.putExtra(DetailUserActivity.EXTRA_USERNAME, data.username)
                intentDetailUser.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                intentDetailUser.putExtra(DetailUserActivity.EXTRA_URL, data.avatar_url)
                startActivity(intentDetailUser)
            }
        })

        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.adapter = userAdapter


            btnSearch.setOnClickListener {
                searchUser()
            }

            etQuery.setOnKeyListener { _, i, keyEvent ->
                if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                    searchUser()
                    return@setOnKeyListener true
                }

                return@setOnKeyListener false
            }
        }


        viewModel.getSearchUsers().observe(this, {
            if (it != null) {
               userAdapter.setList(it)
                showProgressBar(false)
            }
        })

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.favorite_menu -> {
                val intentFavorite = Intent(this, FavoriteActivity::class.java)
                startActivity(intentFavorite)
            }
            R.id.setting_dark_mode -> {
                val intentDarkMode = Intent(this, DarkModeActivity::class.java)
                startActivity(intentDarkMode)
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun showProgressBar(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun userNotFound(state: Boolean) {
        if (state) {
            binding.tvNotFound.visibility = View.VISIBLE
        } else {
            binding.tvNotFound.visibility = View.GONE
        }
    }

    private fun searchUser() {
        binding.apply {
            val query = etQuery.text.toString()
            if (query.isEmpty()) {
                userNotFound(true)
               return
            }
            userNotFound(false)
            showProgressBar(true)
            viewModel.setSearchUsers(query)

        }
    }

}