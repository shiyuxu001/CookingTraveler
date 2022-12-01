package com.example.cookingntraveler

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookingntraveler.api.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    // XXX You need some important member variables
    private var country = "None" //TODO : hmmm

    private val recipeApi = RecipesApi.create()
    private val recipeRepository = RecipeRepository(recipeApi)

    private var processingList = mutableListOf<FilterRecipe>()
    //list that is displayed;
    private val displayedList = MutableLiveData<List<FilterRecipe>>()
    private val categoryList = MutableLiveData<List<String>>()
    //returned by API calls
    private var recipesByCountry = mutableListOf<FilterRecipe>()

    fun convertCountry(area: String) : String {
        Log.d("XXX", "Entered Country: ${area}")
        country = when(area) {
            // Sanitize input
            "Japan" -> "Japanese"
            "United States" -> "American"
            "America" -> "American"
            "US" -> "American"
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
    fun netCountry(selectedCountry : String) {
        viewModelScope.launch (
            context = viewModelScope.coroutineContext + Dispatchers.IO) {
            recipesByCountry.clear()
            val areaList = recipeRepository.getCountryRecipes(selectedCountry).meals
            Log.d("XXX", "List of recipes for ${selectedCountry}: ${areaList}")
            if (areaList != null) {
                for (recipe in areaList) {
                    recipesByCountry.add(recipe)
                }
            }

            displayedList.postValue(recipesByCountry)
        }
    }

    //to filter the recipes displayed when filtering
    fun netFilterCategory(unselectedFilters: MutableList<String>, fullFilterList: MutableList<String>?) {
        viewModelScope.launch(
            context = viewModelScope.coroutineContext + Dispatchers.IO
        ) {

            processingList.clear()
            // reset to include all meals from country
                for (filter in fullFilterList!!) {
                    val categoryRecipes = recipeRepository.getCategoryRecipes(filter).meals
                    if (categoryRecipes != null) {
                        for (recipe in categoryRecipes) {
                            if (isInCategory(recipe)) {
                                processingList.add(recipe)
                            }
                        }
                    }
                }

            // remove meals from unselectedCategories
                for (filter in unselectedFilters) {
                    val categoryRecipes = recipeRepository.getCategoryRecipes(filter).meals
                    if (categoryRecipes != null) {
                        for (recipe in categoryRecipes) {
                            //if its in country list, keep
                                processingList.remove(recipe)
                        }
                    }
                }
                displayedList.postValue(processingList)
            }
    }

    fun netRandomRecipe() {
        viewModelScope.launch (
            context = viewModelScope.coroutineContext + Dispatchers.IO) {

            val random = recipeRepository.getRandomRecipe()
            // TODO, align with design of showing a single recipe
        }
    }

    fun netRetrieveCategories(){
        viewModelScope.launch (
            context = viewModelScope.coroutineContext + Dispatchers.IO) {
            val categories = recipeRepository.getAllCategories().categories
            val allCategories = mutableListOf<String>()
            if (categories != null) {
                for (category in categories) {
                    allCategories.add(category.strCategory)
                }
                categoryList.postValue(allCategories.toList())
            }
        }
    }

    fun observeDisplayedList(): MutableLiveData<List<FilterRecipe>> {
        return displayedList
    }

    fun observeCategoryList(): MutableLiveData<List<String>> {
        return categoryList
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
