package com.silverafederico.apimarvel.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.widget.SearchView
import com.silverafederico.apimarvel.adapter.OnItemClickListen
import com.silverafederico.apimarvel.databinding.ActivityHomeBinding
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.silverafederico.apimarvel.adapter.CharacterAdapter
import com.silverafederico.apimarvel.data.models.MarvelCharacter
import com.silverafederico.apimarvel.databinding.ActivityDetailsCharacterBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity(), OnItemClickListen {
    private lateinit var binding: ActivityHomeBinding
    private val viewModel by viewModel<HomeViewModel>()
    private val handler = Handler(Looper.getMainLooper())
    private val runnable = Runnable {
        viewModel.setSearchQuery(binding.searchBar.query.toString())
    }
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
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 500)
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.setSearchQuery(query)
                return true
            }
        })

    }





    override fun onItemCharacterClick(item: MarvelCharacter) {
        val itemJson = Json.encodeToString(item)
        val intent = Intent(this,DetailsCharacterActivity::class.java)
        intent.putExtra("item", itemJson)
        startActivity(intent)
    }
}