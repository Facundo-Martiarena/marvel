package com.silverafederico.apimarvel.ui.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.silverafederico.apimarvel.adapter.CharacterAdapter
import com.silverafederico.apimarvel.adapter.OnItemClickListen
import com.silverafederico.apimarvel.data.models.MarvelCharacter
import com.silverafederico.apimarvel.databinding.ActivityHomeBinding
import com.silverafederico.apimarvel.ui.auth.LoginActivity
import com.silverafederico.apimarvel.ui.character.DetailsCharacterActivity
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity(), OnItemClickListen {
    private lateinit var binding: ActivityHomeBinding
    private val viewModel by viewModel<HomeViewModel>()
    private val handler = Handler(Looper.getMainLooper())

    private lateinit var auth: FirebaseAuth
    private val runnable = Runnable {
        viewModel.setSearchQuery(binding.searchBar.query.toString())
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
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
        binding.searchBar.setOnClickListener{
            binding.searchBar.isIconified = false
        }

        binding.btnLogout.setOnClickListener{
            logout()
        }

    }

    private fun logout() {
        AlertDialog.Builder(this)
            .setTitle("Cerrar Sesión") // Título del diálogo
            .setMessage("¿Estás seguro de que quieres cerrar sesión?")
            .setPositiveButton("Sí") { dialog, which ->
                auth.signOut()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            .setNegativeButton("No", null)
            .show()
    }



    override fun onItemCharacterClick(item: MarvelCharacter) {
        val itemJson = Json.encodeToString(item)
        val intent = Intent(this, DetailsCharacterActivity::class.java)
        intent.putExtra("item", itemJson)
        startActivity(intent)
    }
}