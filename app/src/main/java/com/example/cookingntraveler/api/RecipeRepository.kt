package com.example.cookingntraveler.api

class RecipeRepository(private val recipeApi: RecipesApi) {

    suspend fun getCountryRecipes(country: String) : List<FilterRecipe>{
        return recipeApi.getCountryRecipes(country)
    }

    suspend fun getCategoryRecipes(category: String): List<FilterRecipe> {
        return recipeApi.getCategoryRecipes(category)
    }

    suspend fun getRecipe(mealId: Int) : Recipe{
        return recipeApi.getRecipe(mealId)
    }

    suspend fun getRandomRecipe() : Recipe {
        return recipeApi.getRandomRecipe()
    }
}