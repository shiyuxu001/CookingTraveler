package com.example.cookingntraveler.api

class RecipeRepository(private val recipeApi: RecipesApi) {

    suspend fun getCountryRecipes(country: String) : FilterRecipes{
        return recipeApi.getCountryRecipes(country)
    }

    suspend fun getCategoryRecipes(category: String): FilterRecipes {
        return recipeApi.getCategoryRecipes(category)
    }

    suspend fun getRecipe(mealId: Long) : SingleRecipe{
        return recipeApi.getRecipe(mealId)
    }

    suspend fun getAllCategories() : Categories {
        return recipeApi.getAllCategories()
    }

    suspend fun getRandomRecipe() : SingleRecipe {
        return recipeApi.getRandomRecipe()
    }
}