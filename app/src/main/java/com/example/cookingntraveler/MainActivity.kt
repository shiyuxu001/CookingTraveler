package com.example.cookingntraveler

import android.R
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
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
    private lateinit var _binding : ContentMainBinding
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    private val recipeTitle = "Recipe"
    private val mapTitle = "Map"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        setSupportActionBar(activityMainBinding.toolbar)
        _binding = activityMainBinding.contentMain

        // TODO: initalize RV, fc 2 reference
        val mapFrag = HomeFragment()
        // TODO: make sure we handle user manually searching a country (must make it in format: Canada ... )
        mapFrag.observeSelectedArea().observe(this) {
            if (!it.equals("Starting")) {

//                viewModel.netRefresh()
                // take into consideration if not a valid country is clicked -> snack bar
                // "no recipes found for area" either: give random recipe/ all recipes or nothing
                // try to see if google maps api has "nearby countries"; continental food
                // logic ... api call?
            }
        }
        mapFrag.observeClickIndicator().observe(this) {
            if (it) {
                Toast.makeText(this, "Please click a valid country!",Toast.LENGTH_SHORT)
            }
            mapFrag.resetInvalidCountryClickIndicator()
        }

        //open fragment
        getSupportFragmentManager()
            .beginTransaction().replace(binding.mapFL.id,mapFrag)
            .addToBackStack(null)
            .commit()

        val recipeFrag = RecipesFragment()
//        recipeFrag.observeCategories().observe(this) {
//            viewModel.netRefresh() // ... find out the call
//        }



    }
}
