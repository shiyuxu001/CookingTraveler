package com.example.cookingntraveler

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookingntraveler.api.FilterRecipe
import com.example.cookingntraveler.api.Recipe
import com.example.cookingntraveler.api.RecipeRepository
import com.example.cookingntraveler.api.RecipesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    // XXX You need some important member variables
    private var country = "American" //TODO : hmmm

    private val recipeApi = RecipesApi.create()
    private val recipeRepository = RecipeRepository(recipeApi)

    private val selectedArea = MutableLiveData<String>()


    private var processingList = mutableListOf<FilterRecipe>()
    //list that is displayed;
    private val displayedList = MutableLiveData<List<FilterRecipe>>()
    //returned by API calls
    private var recipesByCountry = mutableListOf<FilterRecipe>()
    private var recipesByCategory = mutableListOf<FilterRecipe>()
    var fetchDone : MutableLiveData<Boolean> = MutableLiveData(false)


    init {
        netCountry()
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

    //to fetch country list
    fun netCountry() {
        viewModelScope.launch (
            context = viewModelScope.coroutineContext + Dispatchers.IO) {
            convertCountry(selectedArea.value.toString())
            val areaList = recipeRepository.getCountryRecipes(country)
            for (recipe in areaList) {
                recipesByCountry.add(recipe)
            }

            displayedList.postValue(recipesByCountry)
        }
    }

    //to filter the recipes displayed when filtering
    fun netFilterCategory(filters: MutableList<String>){
        viewModelScope.launch (
            context = viewModelScope.coroutineContext + Dispatchers.IO) {

            processingList.clear()
            for(filter in filters) {
                val categoryRecipes = recipeRepository.getCategoryRecipes(filter)
                for(recipe in categoryRecipes) {
                    //if its in country list, keep
                    if(isInCategory(recipe)){
                        processingList.add(recipe)
                    }
                }
            }

            displayedList.postValue(processingList)

        }
    }



    //checks if this recipe is in the country list
    private fun isInCategory(catRec: FilterRecipe): Boolean {
        for(couRec in recipesByCountry){
            if(couRec.idMeal == catRec.idMeal){
                return true;
            }
        }
        return false
    }

}
