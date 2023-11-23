package com.silverafederico.apimarvel.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silverafederico.apimarvel.data.models.MarvelCharacter
import com.silverafederico.apimarvel.data.repositories.CharacterRepository
import kotlinx.coroutines.launch
import java.io.IOException


class HomeViewModel(private val characterRepository: CharacterRepository):ViewModel() {
    private val _characters = MutableLiveData<MarvelCharacter>()
    val characters: LiveData<MarvelCharacter> get() = _characters

//    private val charactersRepository = CharacterRepository

    private val _uiState = MutableLiveData<HomeUIState>()
    val uiState : LiveData<HomeUIState> get() = _uiState

    

    private fun searchCharacters(){
        viewModelScope.launch {

            try {
                val newCharacters = characterRepository.fetchCharacters(_uiState.value?.querySearch)
                val currentState = _uiState.value ?: HomeUIState()
                val newUIState = currentState.copy(characters = newCharacters)
                _uiState.postValue(newUIState)
            } catch (exception: IOException){
                val currentState = _uiState.value ?: HomeUIState()
                _uiState.postValue(currentState.copy(error = exception.message))
                Log.e("searchCharacter","Fallo de carga",exception)
            }
        }
    }
    fun setSearchQuery(query: String) {
        val currentState = _uiState.value ?: HomeUIState()
        _uiState.value = currentState.copy(querySearch = query)
        if(query.length > 0){
            searchCharacters()
        }
    }


}


data class HomeUIState(
    val characters: List<MarvelCharacter> = emptyList(),
    val error: String? = null,
    val querySearch:String? = "",
)

