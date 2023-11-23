package com.silverafederico.apimarvel.ui.comic

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silverafederico.apimarvel.data.models.MarvelCharacter
import com.silverafederico.apimarvel.data.models.MarvelComic
import com.silverafederico.apimarvel.data.repositories.CharacterNameRepository
import com.silverafederico.apimarvel.data.repositories.ComicsRepository
import kotlinx.coroutines.launch
import java.io.IOException

class ComicViewModel(private val characterNameRepository: CharacterNameRepository): ViewModel() {
    private val _character = MutableLiveData<MarvelCharacter>()
    val characters: LiveData<MarvelCharacter> get() = _character

    private val _uiState = MutableLiveData<CharacterNameUIState>()
    val uiState : LiveData<CharacterNameUIState> get() = _uiState



    private fun getComics(){
        viewModelScope.launch {
            try {
                val newCharacter = characterNameRepository.fetchCharacterName(_uiState.value?.comicId)
                val currentState = _uiState.value ?: CharacterNameUIState()
                val newUIState = currentState.copy(characters = newCharacter)
                _uiState.postValue(newUIState)
            } catch (exception: IOException){
                val currentState = _uiState.value ?: CharacterNameUIState()
                _uiState.postValue(currentState.copy(error = exception.message))
                Log.e("runCharacters","Fallo de carga",exception)
            }
        }
    }
    fun runCharacters(comicID:String?){
        val currentState = _uiState.value ?: CharacterNameUIState()
        _uiState.value = currentState.copy(comicId = comicID)
        if (comicID != null){
            getComics()
        }


    }


}


data class CharacterNameUIState(
    val characters: List<MarvelCharacter> = emptyList(),
    val error: String? = null,
    val comicId : String? = "",
)
