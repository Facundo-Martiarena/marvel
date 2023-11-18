package com.silverafederico.apimarvel.ui.character

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silverafederico.apimarvel.data.models.MarvelComic
import com.silverafederico.apimarvel.data.repositories.ComicsRepository
import com.silverafederico.apimarvel.ui.home.HomeUIState
import kotlinx.coroutines.launch
import java.io.IOException

class CharacterViewModel(private val comicsRepository: ComicsRepository): ViewModel() {
    private val _comics = MutableLiveData<MarvelComic>()
    val comics: LiveData<MarvelComic> get() = _comics

    private val _uiState = MutableLiveData<CharacterUIState>()
    val uiState : LiveData<CharacterUIState> get() = _uiState



    private fun getComics(){
        viewModelScope.launch {
            try {
                val newComics = comicsRepository.fetchComics(_uiState.value?.characterId)
                val currentState = _uiState.value ?: CharacterUIState()
                val newUIState = currentState.copy(comics = newComics)
                _uiState.postValue(newUIState)
            } catch (exception: IOException){
                val currentState = _uiState.value ?: CharacterUIState()
                _uiState.postValue(currentState.copy(error = exception.message))
                Log.e("searchComics","Fallo de carga",exception)
            }
        }
    }
    fun runComics(characterID:String?){
        // igual que searchquery
        val currentState = _uiState.value ?: CharacterUIState()
        _uiState.value = currentState.copy(characterId = characterID)
        if (characterID != null){
            getComics()
        }


    }


}


data class CharacterUIState(
    val comics: List<MarvelComic> = emptyList(),
    val error: String? = null,
    val characterId : String? = "",
)
