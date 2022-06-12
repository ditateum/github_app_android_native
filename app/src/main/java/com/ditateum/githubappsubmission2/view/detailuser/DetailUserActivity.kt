package com.ditateum.githubappsubmission2.view.detailuser

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.annotation.StringRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ditateum.githubappsubmission2.R
import com.ditateum.githubappsubmission2.databinding.ActivityDetailUserBinding
import com.ditateum.githubappsubmission2.view.adapter.SectionsPagerAdapter
import com.ditateum.githubappsubmission2.view.darkmode.DarkModeActivity
import com.ditateum.githubappsubmission2.view.favorite.FavoriteActivity
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private val viewModel by viewModels<DetailViewModel>()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Detail User"
        val colorDrawable = ColorDrawable(Color.parseColor("#ED000B1E"))
        supportActionBar?.setBackgroundDrawable(colorDrawable)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_URL)

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        if (username != null) {
            viewModel.setUserDetail(username)
            viewModel.getUserDetail().observe(this, {
                if (it != null) {
                    binding.apply {
                        tvItemNameUser.text = it.name
                        tvItemUsernameUser.text = it.username
                        tvFollowers.text = "${it.followers} Followers"
                        tvFollowing.text = "${it.following} Following"
                        tvRepository.text = "${it.repository} Repository"
                        tvItemCompanyUser.text = "${it.company} - ${it.location}"
                        Glide.with(this@DetailUserActivity)
                            .load(it.avatar_url)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .centerCrop()
                            .into(imgItemAvatar)
                    }
                }
            })
        }

        var isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main){
                if (count != null) {
                    if (count > 0) {
                        binding.toggleFavorite.isChecked = true
                        isChecked = true
                    }else {
                        binding.toggleFavorite.isChecked = false
                        isChecked = false
                    }
                }
            }
        }

        binding.toggleFavorite.setOnClickListener {
            isChecked = !isChecked
            if (isChecked) {
                viewModel.addToFavorite(username!!, id, avatarUrl!!)
            } else {
                viewModel.removeFromFavorite(id)
            }
            binding.toggleFavorite.isChecked = isChecked
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, bundle)
        binding.apply {
            viewPager.adapter = sectionsPagerAdapter
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])

            }.attach()
        }



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

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_URL = "extra_url"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tabs_1,
            R.string.tabs_2
        )
    }
}