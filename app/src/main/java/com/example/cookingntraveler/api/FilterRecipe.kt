package com.example.cookingntraveler.api

import com.google.gson.annotations.SerializedName

data class FilterRecipe (
    @SerializedName("strMeal") val strMeal : String,
    @SerializedName("strMealThumb") val strMealThumb : String,
    @SerializedName("idMeal ")val idMeal : Int
)