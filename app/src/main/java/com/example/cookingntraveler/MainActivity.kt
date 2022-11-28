package com.example.cookingntraveler

import android.R
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.example.cookingntraveler.databinding.ActivityMainBinding
import com.example.cookingntraveler.databinding.ContentMainBinding
import com.google.android.gms.maps.MapFragment
import java.lang.reflect.Array.newInstance


class MainActivity : AppCompatActivity() {


    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding : ContentMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val recipeTitle = "Recipe"
    private val mapTitle = "Map"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        setSupportActionBar(activityMainBinding.toolbar)
        binding = activityMainBinding.contentMain

        val mapFrag = HomeFragment()
        // TODO: make sure we handle user manually searching a country (must make it in format: Canada ... )
//        mapFrag.observeSelectedArea().observe(this) {
//            viewModel.convertCountry(it)
//            viewModel.netRefresh()
//            // take into consideration if not a valid country is clicked -> snack bar
//                // "no recipes found for area" either: give random recipe/ all recipes or nothing
//            // try to see if google maps api has "nearby countries"; continental food
//            // logic ... api call?
//        }
        val recipeFrag = RecipesFragment()
//        recipeFrag.observeCategories().observe(this) {
//            viewModel.netRefresh() // ... find out the call
//        }


        //open fragment
        getSupportFragmentManager()
            .beginTransaction().replace(binding.mapFL.id,mapFrag)
            .commit()
    }

//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.google_map) //?? is this right
//        return navController.navigateUp(appBarConfiguration)
//                || super.onSupportNavigateUp()
//    }
//        setContentView(binding.root)
//        supportFragmentManager
//            .beginTransaction().replace(binding.mapFL.id, HomeFragment.newInstance(mapTitle))
//            .commit()
//
//        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(activityMainBinding.root)
//    }
}

//
//import android.os.Bundle
//import android.util.Log
//import android.view.View
//import android.widget.AdapterView
//import android.widget.ArrayAdapter
//import androidx.activity.viewModels
//import androidx.appcompat.app.AppCompatActivity
//import androidx.fragment.app.commitNow
//import androidx.lifecycle.Observer
//import edu.cs371m.triviagame.databinding.ActivityMainBinding
//import edu.cs371m.triviagame.databinding.ContentMainBinding
//import edu.cs371m.triviagame.ui.main.MainFragment
//
//// https://opentdb.com/api_config.php
//class MainActivity :
//    AppCompatActivity()
//{
//    companion object {
//        val TAG = this::class.java.simpleName
//    }
//    private val frags = listOf(
//        MainFragment.newInstance(0),
//        MainFragment.newInstance(1),
//        MainFragment.newInstance(2)
//    )
//    val difficultyList = listOf("Easy", "Medium", "Hard")
//    lateinit var contentMainBinding: ContentMainBinding
//    private val viewModel: MainViewModel by viewModels()
//    // XXX need to initialize the viewmodel (from an activity)
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val activityMainBiding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(activityMainBiding.root)
//        activityMainBiding.toolbar.title = "Trivia Game"
//        setSupportActionBar(activityMainBiding.toolbar)
//        contentMainBinding = activityMainBiding.contentMain
//        if (savedInstanceState == null) {
//            // XXX Write me: add fragments to layout, swipeRefresh
//            contentMainBinding.swipeRefresh.setOnRefreshListener {
//                viewModel.netRefresh()
//                contentMainBinding.swipeRefresh.isRefreshing = false
//            }
//            supportFragmentManager.commitNow {
//                replace(R.id.q1, frags[0])
//                replace(R.id.q2, frags[1])
//                replace(R.id.q3, frags[2])
//            }
//            viewModel.fetchDone.observe(this) {
//                contentMainBinding.swipeRefresh.isRefreshing = false
//            }
//            // Please enjoy this code that manages the spinner
//            // Create an ArrayAdapter using a simple spinner layout and languages array
//            val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, difficultyList)
//            // Set layout to use when the list of choices appear
//            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            // Create the object as we are assigning it
//            contentMainBinding.difficultySP.onItemSelectedListener = object :
//                AdapterView.OnItemSelectedListener {
//                override fun onItemSelected(parent: AdapterView<*>,
//                                            view: View, position: Int, id: Long) {
//                    Log.d(TAG, "pos $position")
//                    viewModel.setDifficulty(difficultyList[position])
//                }
//
//                override fun onNothingSelected(parent: AdapterView<*>) {
//                    Log.d(TAG, "onNothingSelected")
//                }
//            }
//            // Set Adapter to Spinner
//            contentMainBinding.difficultySP.adapter = aa
//            // Set initial value of spinner to medium
//            val initialSpinner = 1
//            contentMainBinding.difficultySP.setSelection(initialSpinner)
//            viewModel.setDifficulty(difficultyList[initialSpinner])
//        }
//    }
