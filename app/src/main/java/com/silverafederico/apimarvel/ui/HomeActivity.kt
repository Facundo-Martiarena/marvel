package com.silverafederico.apimarvel.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.silverafederico.apimarvel.adapter.OnItemClickListen
import com.silverafederico.apimarvel.databinding.ActivityHomeBinding
import com.silverafederico.apimarvel.databinding.DetailsCharacterBinding
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.silverafederico.apimarvel.adapter.CharacterAdapter
import com.silverafederico.apimarvel.data.models.MarvelCharacter
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity(), OnItemClickListen {
    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.observe(this@HomeActivity){uiState ->
                    if (uiState.characters.isNotEmpty()) {
                        binding.recyclerView.layoutManager = LinearLayoutManager(this@HomeActivity)
                        binding.recyclerView.adapter = CharacterAdapter(uiState.characters,this@HomeActivity)
                    }

                }
            }
        }

    }

    override fun onItemClick(item: MarvelCharacter) {
        val itemJson = Json.encodeToString(item)
        val intent = Intent(this,DetailsCharacterBinding::class.java)
        intent.putExtra("item", itemJson)
        startActivity(intent)
    }
}