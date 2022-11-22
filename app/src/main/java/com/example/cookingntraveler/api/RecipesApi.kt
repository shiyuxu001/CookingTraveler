package com.example.cookingntraveler.api

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipesApi {

    @GET("filter.php?")
    suspend fun getCountryRecipes(@Query("a") level: String) : List<FilterRecipe>
    suspend fun getCategoryRecipes(@Query("c") category: String): List<FilterRecipe>
    suspend fun getIngredientsRecipes(@Query("i") ingredient: String): List<FilterRecipe>

    @GET("lookup.php")
    suspend fun getRecipe(@Query("i") mealId: Int) : Recipe

    @GET("random.php")
    suspend fun getRandomRecipe() :Recipe

    companion object {
        // Leave this as a simple, base URL.  That way, we can have many different
        // functions (above) that access different "paths" on this server
        // https://square.github.io/okhttp/4.x/okhttp/okhttp3/-http-url/
        var url = HttpUrl.Builder()
            .scheme("https")
            .host("www.themealdb.com/api/json/v1/1")
            .build()

        // Public create function that ties together building the base
        // URL and the private create function that initializes Retrofit
        fun create(): RecipesApi = create(url)
        private fun create(httpUrl: HttpUrl): RecipesApi {
            val client = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    this.level = HttpLoggingInterceptor.Level.BASIC
                })
                .build()
            return Retrofit.Builder()
                .baseUrl(httpUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RecipesApi::class.java)
        }
    }

}