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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        setSupportActionBar(activityMainBinding.toolbar)
        _binding = activityMainBinding.contentMain

        // TODO: initalize RV, fc 2 reference
        val mapFrag = HomeFragment()

        //open fragment
        // TODO: IMPORTANT update layouts properly or else app won't start
        getSupportFragmentManager()
            .beginTransaction().replace(binding.mapFL.id,mapFrag)
            .addToBackStack(null)
            .commit()

        val recipeFrag = RecipesFragment() // TODO: need to create on click function to pass in + RV

        mapFrag.observeSelectedArea().observe(this) {
            if (!it.equals("Starting")) {
                // view model functions ...
//                viewModel.netRefresh()
                // depending on what the view model returns
                // if no recipes for this country -> make toast, ask for them to enter new country
                // else replace fragment to be recipes something like:
//                getSupportFragmentManager()
//                    .beginTransaction().replace(binding.mapFL.id, )
//                    .commit()

//            }
            }
        }
        mapFrag.observeClickIndicator().observe(this) {
            if (it) {
                Toast.makeText(this, "Please click a valid country!",Toast.LENGTH_SHORT)
            }
            mapFrag.resetInvalidCountryClickIndicator()
        }


        recipeFrag.observeCategories().observe(this) {
            // view model functions ...
//            viewModel.netRefresh() // ... TODO: view model functions to call
        }

    }
}
