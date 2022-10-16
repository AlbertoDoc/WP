package com.example.wps.events.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.wps.R
import com.example.wps.databinding.MediaActivityBinding

class MediaActivity : AppCompatActivity() {

    private lateinit var binding: MediaActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MediaActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUrl : String? = intent.getStringExtra("event_image")

        Glide.with(this)
            .load(imageUrl)
            .error(R.drawable.ic_round_image_108)
            .placeholder(R.drawable.ic_round_image_108)
            .into(binding.image)
    }
}