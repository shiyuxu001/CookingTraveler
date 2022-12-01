package com.example.cookingntraveler

import android.R
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cookingntraveler.api.FilterRecipe
import com.example.cookingntraveler.databinding.RowBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy


class RecipeRVAdapter(context: Context)
    : ListAdapter<FilterRecipe,
        RecipeRVAdapter.ViewHolder>(Diff()) {
    private val context = context
    var createPopUp = MutableLiveData<Boolean>().default(false)
    var mealId = MutableLiveData<Long>().default(0)

    private fun getPos(holder: ViewHolder) : Int {
        val pos = holder.adapterPosition
        // notifyDataSetChanged was called, so position is not known
//        if( pos == RecyclerView.NO_POSITION) {
//            return holder.getA
//        }
        return pos
    }

    inner class ViewHolder(val recipeRowBinding : RowBinding)
        : RecyclerView.ViewHolder(recipeRowBinding.root) {
            init {
                recipeRowBinding.root.setOnClickListener{
                    val recipe = getItem(getPos(this))
                    notifyDataSetChanged()
                    Log.d("XXX", "MEALID ${recipe.idMeal}")
                    mealId.value = recipe.idMeal
                    createPopUp.value = true
                }
            }
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val recipeBinding = RowBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return ViewHolder(recipeBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(holder.adapterPosition)
        val rowBinding = holder.recipeRowBinding
        rowBinding.recipeTitle.text = item.strMeal
        Glide.with(context)
            .load(item.strMealThumb)
            .placeholder(com.example.cookingntraveler.R.drawable.ingredients)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(rowBinding.recipeImage)
    }

    class Diff : DiffUtil.ItemCallback<FilterRecipe>() {
        // Item identity
        override fun areItemsTheSame(oldItem: FilterRecipe, newItem: FilterRecipe): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
        // Item contents are the same, but the object might have changed
        override fun areContentsTheSame(oldItem: FilterRecipe, newItem: FilterRecipe): Boolean {
            return oldItem.idMeal == newItem.idMeal
                    && oldItem.strMeal == newItem.strMeal
                    && oldItem.strMealThumb == newItem.strMealThumb
        }
    }
    fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }

    fun observeCreatePopUp() : MutableLiveData<Boolean> {
        return createPopUp
    }

    fun observeMealId() : MutableLiveData<Long> {
        return mealId
    }
}
