package com.example.myrpgcharacter.translation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TranslationViewModel(private val repository: TranslationRepository) : ViewModel() {

    val translatedText: LiveData<String> get() = repository.translatedText

    fun translateText(language: String, text: String) {
        viewModelScope.launch {
            repository.translateText(language, text)
        }
    }
}