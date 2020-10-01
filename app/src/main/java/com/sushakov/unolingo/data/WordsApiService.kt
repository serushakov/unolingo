package com.sushakov.unolingo.data

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.sushakov.unolingo.data.word.Word
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://users.metropolia.fi/~sergeyu/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface WordsApiService {
    @GET("words.json")
    suspend fun getWords(): List<List<Word>>
}

object WordsApi {
    val retrofitService : WordsApiService by lazy {
        retrofit.create(WordsApiService::class.java)
    }
}