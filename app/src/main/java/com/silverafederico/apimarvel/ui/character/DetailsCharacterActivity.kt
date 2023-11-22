package com.silverafederico.apimarvel.ui.character

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import coil.load
import com.silverafederico.apimarvel.R
import com.silverafederico.apimarvel.adapter.CharacterAdapter
import com.silverafederico.apimarvel.adapter.ComicAdapter
import com.silverafederico.apimarvel.adapter.OnItemComicClickListen
import com.silverafederico.apimarvel.data.models.MarvelCharacter
import com.silverafederico.apimarvel.data.models.MarvelComic
import com.silverafederico.apimarvel.databinding.ActivityDetailsCharacterBinding
import com.silverafederico.apimarvel.ui.comic.DetailsComicActivity
import com.silverafederico.apimarvel.ui.home.HomeViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsCharacterActivity : AppCompatActivity(), OnItemComicClickListen {
    private lateinit var binding:ActivityDetailsCharacterBinding
    private val viewModel by viewModel<CharacterViewModel>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsCharacterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val itemJson = intent.getStringExtra("item")
        val item: MarvelCharacter? = itemJson?.let { Json.decodeFromString(it) }


        binding.name.text = item?.name
        binding.description.text = item?.description
        binding.imageView.load(item?.image){
            crossfade(true)
            placeholder(R.drawable.image_placeholder)
        }
        viewModel.runComics(item?.id.toString())
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.observe(this@DetailsCharacterActivity){uiState ->
                    if (uiState.comics.isNotEmpty()) {
                        binding.recyclerView.layoutManager =
                            LinearLayoutManager(this@DetailsCharacterActivity, HORIZONTAL, false)
                        binding.recyclerView.adapter =
                            ComicAdapter(uiState.comics, this@DetailsCharacterActivity)
                    }

                }
            }
        }


    }

    override fun onItemComicClick(item: MarvelComic) {
        val itemJson = Json.encodeToString(item)
        val intent = Intent(this, DetailsComicActivity::class.java)
        intent.putExtra("item", itemJson)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        val itemJson = intent.getStringExtra("item")
        val item: MarvelCharacter? = itemJson?.let { Json.decodeFromString(it) }
        Toast.makeText(this,"${item?.id}",Toast.LENGTH_SHORT).show()
        viewModel.runComics(item?.id.toString())
    }
}
