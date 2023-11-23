package com.silverafederico.apimarvel.ui.comic

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.silverafederico.apimarvel.R
import com.silverafederico.apimarvel.adapter.CharacterHorizontalAdapter
import com.silverafederico.apimarvel.adapter.ComicAdapter
import com.silverafederico.apimarvel.data.models.MarvelCharacter
import com.silverafederico.apimarvel.data.models.MarvelComic
import com.silverafederico.apimarvel.databinding.ActivityDetailsCharacterBinding
import com.silverafederico.apimarvel.databinding.ActivityDetailsComicBinding
import com.silverafederico.apimarvel.ui.character.CharacterViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsComicActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsComicBinding
    private val viewModel by viewModel<ComicViewModel>()
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
        viewModel.runCharacters(item?.id.toString())
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.observe(this@DetailsComicActivity){uiState ->
                    if (uiState.characters.isNotEmpty()) {
                        binding.recyclerView.layoutManager =
                            LinearLayoutManager(this@DetailsComicActivity, LinearLayoutManager.HORIZONTAL, false)
                        binding.recyclerView.adapter =
                            CharacterHorizontalAdapter(uiState.characters)
                    }

                }
            }
        }

    }
}