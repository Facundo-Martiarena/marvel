package com.silverafederico.apimarvel.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.silverafederico.apimarvel.databinding.DetailsCharacterBinding
import coil.load
import com.silverafederico.apimarvel.R
import com.silverafederico.apimarvel.data.models.MarvelCharacter
import kotlinx.serialization.json.Json

class DetailsCharacterActivity : AppCompatActivity() {
    private lateinit var binding:DetailsCharacterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailsCharacterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val itemJson = intent.getStringExtra("item")
        val item: MarvelCharacter? = itemJson?.let { Json.decodeFromString(it) }
        binding.name.text = item?.name
        binding.description.text = item?.description
        binding.imageView.load(item?.image){
            crossfade(true)
            placeholder(R.drawable.image_placeholder)
        }

    }
}