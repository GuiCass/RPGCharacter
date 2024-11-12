package com.example.myrpgcharacter.translation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myrpgcharacter.api.TranslationApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TranslationRepository(private val apiService: TranslationApi) {

    // LiveData to observe the translation result
    private val _translatedText = MutableLiveData<String>()
    val translatedText: LiveData<String> get() = _translatedText

    suspend fun translateText(language: String, text: String) {
        try {
            // Make the API call directly
            val response = withContext(Dispatchers.IO) {
                apiService.translateText(language, text)
            }

            // Handle the response, provide a default value if null
            val translatedText = response?.contents?.translatedText ?: "Translation unavailable"
            _translatedText.postValue(translatedText)

        } catch (e: Exception) {
            Log.e("API_ERROR", "Network request failed: ${e.message}")
            _translatedText.postValue("Error: Could not translate text")
        }
    }
}