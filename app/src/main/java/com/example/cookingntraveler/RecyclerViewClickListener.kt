package com.example.cookingntraveler

import android.view.View

interface RecyclerViewClickListener {
    fun recyclerViewListClicked(v: View?, position: Int)
}