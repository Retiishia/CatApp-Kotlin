package com.dicoding.picodiploma.catapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.dicoding.picodiploma.catapp.R

class OpeningScreenActivity : AppCompatActivity() {
    private val handler = Handler(Looper.getMainLooper())
    private val delay : Int = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opening_screen)

        handler.postDelayed({
            val intent = Intent(this, MainActivity::class.java)

            startActivity(intent)
            finish()
        }, delay.toLong())
    }
}