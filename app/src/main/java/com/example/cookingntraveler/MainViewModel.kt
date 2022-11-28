package com.example.cookingntraveler

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookingntraveler.api.FilterRecipe
import com.example.cookingntraveler.api.RecipeRepository
import com.example.cookingntraveler.api.RecipesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    // XXX You need some important member variables
    private var country = "American" //TODO : hmmm
    private var categories = listOf<String>()
    private val recipeApi = RecipesApi.create()
    private val recipeRepository = RecipeRepository(recipeApi)
    private val recipesList = MutableLiveData<List<FilterRecipe>>()
    private val displayedList = MutableLiveData<List<FilterRecipe>>()
    private val selectedArea = MutableLiveData<String>()
    private var recipesByCountry = listOf<String>()
    var fetchDone : MutableLiveData<Boolean> = MutableLiveData(false)
    init {
        netRefresh()
    }


    fun convertCountry(area: String) : String {

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
            "Spain" -> "Spanish"
            "Tunisia" -> "Tunisian"
            "Portugal" -> "Portuguese"
            else -> "None"
        }
        return country
    }

    fun netRefresh() {
        viewModelScope.launch (
            context = viewModelScope.coroutineContext + Dispatchers.IO) {
//            convertCountry(selectedArea.value.toString())

//            recipesList.postValue(recipeRepository.getCountryRecipes(country))
//            for (category in categories) {
//                recipesList.postValue(recipeRepository.getCategoryRecipes(category))
//            }
        }
    }

    // only if a filter has been selected do this logic:
    fun reduceRecipesShown() {
        // store the what's being returned from the category call into a variable, making it each to
        // remove when a category is unselected
    }
}
