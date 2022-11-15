package com.example.cookingntraveler.api

class RecipeRepository(private val recipeApi: RecipesApi) {

    // TODO unwrap the responses for our use
    suspend fun getCountryRecipes(country: String) {
        recipeApi.getCountryRecipes(country)
    }

    suspend fun getCategoryRecipes(category: String) {
        recipeApi.getCategoryRecipes(category)
    }

    suspend fun getIngredientsRecipes(ingredient: String) {
        recipeApi.getIngredientsRecipes(ingredient)
    }

    suspend fun getRecipe(mealId: Int) {
        recipeApi.getRecipe(mealId)
    }

    suspend fun getRandomRecipe() {
        recipeApi.getRandomRecipe()
    }
}