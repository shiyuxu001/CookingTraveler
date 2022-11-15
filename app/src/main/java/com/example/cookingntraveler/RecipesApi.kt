package com.example.cookingntraveler

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call

class RecipesApi {

    // This function needs to be called from a coroutine, hence the suspend
    // in its type.  Also note the @Query annotation, which says that when
    // called, retrofit will add "&difficulty=%s".format(level) to the URL
    // Thanks, retrofit!
    // Hardcode several parameters in the GET for simplicity
    // So URL can have & and ? characters
    // XXX Write me: Retrofit annotation, see CatNet
    @GET("filter.php?")
    suspend fun getCountryRecipes(@Query("country") level: String) : Call<List<Recipe>>

    suspend fun getCategoryRecipes(): Call<List<Recipe>>

    @GET("lookup.php")
    suspend fun getRecipe()
    suspend fun
    @GET("random.php")
    suspend fun getRandomrecipe() : Call<Recipe>




    // I just looked at the response and "parsed" it by eye
    data class TriviaResponse(val results: List<TriviaQuestion>)

    companion object {
        // Leave this as a simple, base URL.  That way, we can have many different
        // functions (above) that access different "paths" on this server
        // https://square.github.io/okhttp/4.x/okhttp/okhttp3/-http-url/
        var url = HttpUrl.Builder()
            .scheme("https")
            .host("themealdb.com")
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