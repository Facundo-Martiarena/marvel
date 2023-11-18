package com.silverafederico.apimarvel.ui.comic

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.silverafederico.apimarvel.R
import com.silverafederico.apimarvel.data.models.MarvelCharacter
import com.silverafederico.apimarvel.data.models.MarvelComic
import com.silverafederico.apimarvel.databinding.ActivityDetailsCharacterBinding
import com.silverafederico.apimarvel.databinding.ActivityDetailsComicBinding
import kotlinx.serialization.json.Json

class DetailsComicActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsComicBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsComicBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val itemJson = intent.getStringExtra("item")
        val item: MarvelComic? = itemJson?.let { Json.decodeFromString(it) }
        binding.name.text = item?.title
        binding.description.text = item?.description
        binding.imageView.load(item?.image){
            crossfade(true)
            placeholder(R.drawable.image_placeholder)
        }

    }
}