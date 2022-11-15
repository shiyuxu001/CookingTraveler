package com.example.cookingntraveler

import com.google.gson.annotations.SerializedName

data class FilterRecipes (
    @SerializedName("strMeal") val strMeal : String,
    @SerializedName("strMealThumb") val strMealThumb : String,
    @SerializedName("idMeal ")val idMeal : Int,
    )