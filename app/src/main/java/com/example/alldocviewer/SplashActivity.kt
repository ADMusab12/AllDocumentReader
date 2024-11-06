package com.example.alldocviewer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.alldocviewer.databinding.ActivitySplashBinding
import com.example.alldocviewer.util.Extension.setStatusBarColor

class SplashActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarColor(R.color.white)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        binding.lottie.playAnimation()
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@SplashActivity,MainActivity::class.java))
        },3000)
    }

    override fun onPause() {
        super.onPause()
        binding.lottie.pauseAnimation()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.lottie.pauseAnimation()
    }
}