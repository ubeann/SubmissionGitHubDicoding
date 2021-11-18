package com.dicoding.submissiongithub

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    // Variable Setup
    private val loadingTime:Long = 5000 //Delay of 5 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
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