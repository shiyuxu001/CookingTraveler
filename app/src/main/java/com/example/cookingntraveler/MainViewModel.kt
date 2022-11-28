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
    private var recipesByCountry = mutableListOf<FilterRecipe>()
    private var recipesByCategory = mutableListOf<FilterRecipe>()
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
            convertCountry(selectedArea.value.toString())
            val areaList = recipeRepository.getCountryRecipes(country)
            for (recipe in areaList) {
                recipesByCountry.add(recipe)
            }
            for (category in categories) {
               val categoryList = recipeRepository.getCategoryRecipes(category)
                for (recipe in categoryList) {
                    recipesByCategory.add(recipe)
                }
            }
            // make call to reduce recipes shown ()
            // postValue on recipesList and displayed recipes list
            // ONLY CALL REDUCE RECIPES if categories is not empty

        }
    }

    // only if a filter has been selected do this logic:
    fun reduceRecipesShown() {
        // using recipes by category and recipes by Country, do cross referencing, this function reduceRecipesShow()
        // is a helper method for net refresh
    }
}
