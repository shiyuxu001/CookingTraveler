package com.example.cookingntraveler

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.example.cookingntraveler.databinding.ActivityMainBinding
import com.example.cookingntraveler.databinding.ContentMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding : ContentMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        setSupportActionBar(activityMainBinding.toolbar)
        binding = activityMainBinding.contentMain

        val mapFrag = HomeFragment()

        //open fragment
        getSupportFragmentManager()
            .beginTransaction().replace(R.id.google_map,mapFrag)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.google_map) //?? is this right
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
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
