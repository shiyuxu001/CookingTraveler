package com.example.cookingntraveler.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SingleRecipe(
    @SerializedName("meals")
    @Expose
    val meals: List<Recipe>? = null
)