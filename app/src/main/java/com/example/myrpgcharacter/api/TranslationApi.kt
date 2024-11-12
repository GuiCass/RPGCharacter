// TranslationApi.kt
package com.example.myrpgcharacter.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TranslationApi {

    @GET("/translate/{language}.json") // `{language}` é o tipo de tradução
    suspend fun translateText(
        @Path("language") language: String, // Adiciona a linguagem como um parâmetro de caminho
        @Query("text") text: String
    ): TranslationResponse

    companion object {
        private const val BASE_URL = "https://api.funtranslations.com/" // Replace with your base URL

        fun create(): TranslationApi {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(TranslationApi::class.java)
        }
    }
}
