package com.example.cookingntraveler

import android.R
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.navigation.ui.AppBarConfiguration
import com.example.cookingntraveler.databinding.ActivityMainBinding
import com.example.cookingntraveler.databinding.ContentMainBinding


class MainActivity : AppCompatActivity(), LoadingImplementation {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var _binding : ContentMainBinding
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    private lateinit var activityMainBinding : ActivityMainBinding
    var randomRecipeCountry = ""


    //for loading animation
    private lateinit var loadingAnimation : LoadingAnimation


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        setSupportActionBar(activityMainBinding.toolbar)
        _binding = activityMainBinding.contentMain


        viewModel.netRetrieveCategories()

        val mapFrag = HomeFragment()
        val recipeFrag = RecipesFragment()

        //open fragment
        getSupportFragmentManager()
            .beginTransaction().replace(binding.mapFL.id,mapFrag)
            .commit()

        binding.random.setOnClickListener {
            viewModel.netRandomRecipe()
            viewModel.observeSingleRecipe().observe(this){
                if(it != null) {
                    val singleRecipeFrag = SingleRecipeFragment.newInstance(it.meals!!.get(0).strMeal!!,
                        it.meals!!.get(0).strInstructions!!, it.meals!!.get(0).strMealThumb!!)
                    randomRecipeCountry = it.meals!!.get(0).strArea!!
                    supportFragmentManager.beginTransaction()
                        .replace(binding.mapFL.id, singleRecipeFrag)
                        .commit()
                    singleRecipeFrag.observeLeaveDialog().observe(this) {
                        if (it) {
                            viewModel.netCountry(randomRecipeCountry)
                            supportFragmentManager.beginTransaction()
                                .replace(binding.mapFL.id, recipeFrag)
                                .commit()
                        }
                    }

                }

            }
        }

        mapFrag.observeInputtedCountry().observe(this) {
        if(!it.equals("Starting")) {
            if (it.isEmpty()) {
                Toast.makeText(this, "Please enter a country name!", Toast.LENGTH_SHORT).show()
            }
            if (it.equals("None")) {
                Toast.makeText(
                    this,
                    "We don't have recipes from this country yet :'(",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                viewModel.netCountry(it)
//                //added loading animation
//                loadingAnimation = LoadingAnimation(this, "lottie_plane_anim.json")
//                loadingAnimation.playAnimation(true)
////                LoadingAsync(this).execute()
//                loadingAnimation.stopAnimation(activityMainBinding.root) //?? umm
//                //then fragment replace thing: recipesFrag into here
                supportFragmentManager.beginTransaction()
                    .replace(binding.mapFL.id, recipeFrag)
                    .commit()
            }
        }
        }

        viewModel.observeDisplayedList().observe(this) {
            if(it.isEmpty()) {
                Toast.makeText(this,"No recipes match applied filter(s)", Toast.LENGTH_SHORT).show()
            }
        }

        mapFrag.observeSelectedArea().observe(this) {
            if (!it.equals("Starting")) {
                val countryEntered = viewModel.convertCountry(it)
                if (countryEntered.equals("None")) {
                    Log.d("XXX", "No recipes found")

                    Toast.makeText(this, "We don't have recipes from this country yet :'(", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.netCountry(countryEntered)
                    supportFragmentManager.beginTransaction()
                        .replace(binding.mapFL.id, recipeFrag)
                        .commit()
                }
            }
        }

        mapFrag.observeClickIndicator().observe(this) {
            if (it) {
                Toast.makeText(this, "Please click a valid country!",Toast.LENGTH_SHORT)
            }
        }

        recipeFrag.observerBackButtonPushed().observe(this) {
            if(it) {
                supportFragmentManager.beginTransaction()
                    .replace(binding.mapFL.id, mapFrag)
                    .commit()
            }

        }


        recipeFrag.observeCommunicateWithMain().observe(this) {
            if (it) {
                Log.d("XXX", "row adapter was clicked")
                recipeFrag.observeSendMainMealId().observe(this) {
                    viewModel.netSingleRecipe(it)
                    viewModel.observeSingleRecipe().observe(this){
                        if (it != null) {
                            val singleRecipeFrag = SingleRecipeFragment.newInstance(it.meals!!.get(0).strMeal!!,
                                it.meals!!.get(0).strInstructions!!, it.meals!!.get(0).strMealThumb!!)
                            supportFragmentManager.beginTransaction()
                                .replace(binding.mapFL.id, singleRecipeFrag)
                                .commit()
                            singleRecipeFrag.observeLeaveDialog().observe(this) {
                                if (it) {
                                    viewModel.netCountry(randomRecipeCountry)
                                    supportFragmentManager.beginTransaction()
                                        .replace(binding.mapFL.id, recipeFrag)
                                        .commit()
                                    }
                                }
                            }

                        }
                    }
                }
        }

    }
    override fun onFinishedLoading() {
        loadingAnimation.stopAnimation(activityMainBinding.root) //?
    }
    fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }


}
