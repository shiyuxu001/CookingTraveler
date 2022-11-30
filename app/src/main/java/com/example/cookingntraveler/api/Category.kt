package com.example.cookingntraveler.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Category (
    @SerializedName("idCategory")
    @Expose
    val idCategory: String? = null,

        @SerializedName("strCategory")
    val strCategory: String,

    @SerializedName("strCategoryThumb")
    @Expose
    val strCategoryThumb: String? = null,

    @SerializedName("strCategoryDescription")
    @Expose
    val strCategoryDescription: String? = null
)
