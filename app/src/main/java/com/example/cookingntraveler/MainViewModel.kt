package com.example.cookingntraveler

import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookingntraveler.api.FilterRecipe
import com.example.cookingntraveler.api.RecipeRepository
import com.example.cookingntraveler.api.RecipesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.cookingntraveler.glide.Glide
import java.util.*

class MainViewModel : ViewModel() {
    // XXX You need some important member variables
    private var country = "American"
    private var categories = listOf<String>()
    private val recipeApi = RecipesApi.create()
    private val recipeRepository = RecipeRepository(recipeApi)
    private val recipesList = MutableLiveData<List<FilterRecipe>>()
    private val selectedArea = MutableLiveData<String>()
    var fetchDone : MutableLiveData<Boolean> = MutableLiveData(false)
    init {
        netRefresh()
    }

    fun convertCountry(area: String) {
        country = when(area) {
            // Sanitize input
            "Japan" -> "Japanese"
            "United States of America" -> "American"
            "Canada" -> "Canadian"
            "United Kingdom" -> "British"
            "China" -> "Chinese"
            "France" -> "French"
            "Egypt" -> "Egyptian"
            "Netherlands" -> "Dutch"
            "Croatia" -> "Croatian"
            "Greece" -> "Greek"
            "Vietnam" -> "Vietnamese"
            "Turkey" -> "Turkish"
            "Thailand" -> "Thai"
            "Italy" -> "Italian"
            "Ireland" -> "Irish"
            "India" -> "Indian"
            "Italian" -> "Italian"
            "Kenya" -> "Kenyan"
            "Jamaica" -> "Jamaican"
            "Malaysia" -> "Malaysian"
            "Poland" -> "Polish"
            "Morocco" -> "Moroccan"
            "Mexico" -> "Mexican"
            "Russia" -> "Russian"
            "Spain" -> "Spainish"
            "Tunisia" -> "Tunisian"
            "Portugal" -> "Portuguese"
            else -> "None"
        }
    }

    fun netRefresh() {
        // XXX Write me.  This is where the network request is initiated.
        viewModelScope.launch (
            context = viewModelScope.coroutineContext + Dispatchers.IO) {
            convertCountry(selectedArea.value.toString())
            recipesList.postValue(recipeRepository.getCountryRecipes(country))
            for (category in categories) {
                recipesList.postValue(recipeRepository.getCategoryRecipes(category))
            }
        }
    }
}
