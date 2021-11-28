package com.dicoding.submissiongithub

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dicoding.submissiongithub.datastore.SettingPreferences
import com.dicoding.submissiongithub.factory.SettingsViewModelFactory
import com.dicoding.submissiongithub.view_model.SettingsViewModel

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    // Variable Setup
    private val loadingTime:Long = 5000 //Delay of 5 seconds
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private lateinit var preferences: SettingPreferences
    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        // Check and observe theme
        preferences = SettingPreferences.getInstance(dataStore)
        settingsViewModel = ViewModelProvider(this, SettingsViewModelFactory(preferences))[SettingsViewModel::class.java]
        settingsViewModel.getThemeSettings().observe(this, {
            AppCompatDelegate.setDefaultNightMode(if (it) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
        })

        // Splash
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        // Navigate main activity
        val splashThread: Thread = object : Thread() {
            override fun run() {
                try {
                    super.run()
                    sleep(loadingTime)
                } catch (e: Exception) {
                } finally {
                    val intent = Intent(
                        this@SplashActivity,
                        MainActivity::class.java
                    )
                    startActivity(intent)
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                    finish()
                }
            }
        }
        splashThread.start()
    }
}