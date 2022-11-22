package com.example.cookingntraveler

import android.widget.ImageView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookingntraveler.api.RecipeRepository
import com.example.cookingntraveler.api.RecipesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.cookingntraveler.glide.Glide

class MainViewModel : ViewModel() {
    // XXX You need some important member variables
    private val recipeApi = RecipesApi.create()
    private val triviaRepository = RecipeRepository(recipeApi)
//    private val triviaQuestion = MutableLiveData<List<TriviaQuestion>>()
//    var fetchDone : MutableLiveData<Boolean> = MutableLiveData(false)
    init {
        netRefresh()
    }

//    fun setDifficulty(level: String) {
//        difficulty = when(level.lowercase(Locale.getDefault())) {
//            // Sanitize input
//            "easy" -> "easy"
//            "medium" -> "medium"
//            "hard" -> "hard"
//            else -> "medium"
//        }
//        Log.d(javaClass.simpleName, "level $level END difficulty $difficulty")
//    }

    fun netRefresh() {
        // XXX Write me.  This is where the network request is initiated.
        viewModelScope.launch (
            context = viewModelScope.coroutineContext + Dispatchers.IO) {
//            triviaQuestion.postValue(triviaRepository.getThree(difficulty))
        }
    }

    fun netFetchImage(imageView: ImageView) {
//        Glide.fetch(randomPiscumURL(), safePiscumURL(), imageView)
    }

//    // XXX Another function is necessary
//    fun observeTriviaQuestion(): LiveData<List<TriviaQuestion>> {
//        return triviaQuestion
//    }
}
