package com.ditateum.githubappsubmission2.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.ditateum.githubappsubmission2.R
import com.ditateum.githubappsubmission2.utils.SettingPreferences
import com.ditateum.githubappsubmission2.view.darkmode.DarkModeViewModel
import com.ditateum.githubappsubmission2.view.darkmode.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var darkViewModel : DarkModeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()

        val duration = 3000L

        Handler(Looper.getMainLooper()).postDelayed({
            val intentSplash = Intent(this, MainActivity::class.java)
            startActivity(intentSplash)
            finish()
        }, duration)

        val pref = SettingPreferences.getInstance(dataStore)
        darkViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            DarkModeViewModel::class.java
        )
        darkViewModel.getThemeSettings().observe(this,
            { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            })

    }
}