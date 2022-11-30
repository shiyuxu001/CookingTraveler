package com.example.cookingntraveler.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FilterRecipes (
    @SerializedName("meals")
    @Expose
    val meals: List<FilterRecipe>? = null,
)