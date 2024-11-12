package com.example.myrpgcharacter.api

import com.google.gson.annotations.SerializedName

data class TranslationResponse(
    @SerializedName("contents") val contents: TranslationContents
)

data class TranslationContents(
    @SerializedName("translated") val translatedText: String
)
