package com.silverafederico.apimarvel.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silverafederico.apimarvel.data.models.MarvelCharacter
import com.silverafederico.apimarvel.data.repositories.CharacterRepository
import kotlinx.coroutines.launch
import java.io.IOException


class HomeViewModel:ViewModel() {
    private val _characters = MutableLiveData<MarvelCharacter>()
    val characters: LiveData<MarvelCharacter> get() = _characters

    private val charactersRepository = CharacterRepository

    private val _uiState = MutableLiveData<HomeUIState>()
    val uiState : LiveData<HomeUIState> get() = _uiState


    init {
        fetchCharacters()
    }

    private fun fetchCharacters(){
        viewModelScope.launch {
            try {
                val newCharacters = charactersRepository.fetchCharacters()
                val currentState = _uiState.value ?: HomeUIState()
                val newUIState = currentState.copy(characters = newCharacters)
                _uiState.postValue(newUIState)
            } catch (exception: IOException){
                val currentState = _uiState.value ?: HomeUIState()
                _uiState.postValue(currentState.copy(error = exception.message))
            }
        }
    }

}

data class HomeUIState(
    val characters: List<MarvelCharacter> = emptyList(),
    val error: String? = null
)

//private val repository = CharacterRepository
//private val _characters = MutableLiveData<List<MarvelCharacter>>()
//val characters: LiveData<List<MarvelCharacter>>
//    get() = _characters
//
//fun fetchCharacter(){
//    viewModelScope.launch{
//        repository.fetchCharacters().run {
//            _characters.postValue(this)
//        }
//    }
//}