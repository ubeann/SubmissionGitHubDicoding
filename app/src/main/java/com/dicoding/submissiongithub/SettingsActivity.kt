package com.dicoding.submissiongithub

import android.content.Context
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dicoding.submissiongithub.databinding.ActivitySettingsBinding
import com.dicoding.submissiongithub.datastore.SettingPreferences
import com.dicoding.submissiongithub.factory.SettingsViewModelFactory
import com.dicoding.submissiongithub.view_model.SettingsViewModel

class SettingsActivity : AppCompatActivity() {
    // Variable Setup
    private var binding: ActivitySettingsBinding? = null
    private val title:String = "Settings"
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private lateinit var preferences: SettingPreferences
    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // Setup title action bar
        val actionBar = supportActionBar
        actionBar?.title = title

        // Set variable
        preferences = SettingPreferences.getInstance(dataStore)
        settingsViewModel = ViewModelProvider(this, SettingsViewModelFactory(preferences))[SettingsViewModel::class.java]

        // Observe theme changed
        settingsViewModel.getThemeSettings().observe(this, {
            setDarkMode(it)
        })

        binding?.switchTheme?.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            settingsViewModel.saveThemeSetting(isChecked)
        }
    }

    private fun setDarkMode(isDarkModeActive: Boolean) {
        AppCompatDelegate.setDefaultNightMode(if (isDarkModeActive) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
        binding?.switchTheme?.isChecked = isDarkModeActive
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}