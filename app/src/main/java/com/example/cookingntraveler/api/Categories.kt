package com.example.cookingntraveler.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Categories (
    @SerializedName("categories")
    @Expose
    val categories: List<Category>? = null
)