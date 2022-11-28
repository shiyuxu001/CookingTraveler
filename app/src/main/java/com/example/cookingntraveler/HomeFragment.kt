package com.example.cookingntraveler
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import com.example.cookingntraveler.databinding.MapFragmentBinding

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;



/*
//import android.content.Context
//import android.util.AttributeSet
//import android.view.View
//import com.example.cookingntraveler.databinding.ContentMainBinding
//
//
//import android.Manifest
//import android.content.pm.PackageManager
//import android.location.Geocoder
//import android.os.Bundle
//import android.util.Log
//import android.view.KeyEvent
//import android.view.inputmethod.EditorInfo
//import android.view.inputmethod.InputMethodManager
//import android.widget.Toast
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.appcompat.app.AppCompatActivity
//import com.google.android.gms.common.ConnectionResult
//import com.google.android.gms.common.GoogleApiAvailability
//import com.google.android.gms.maps.CameraUpdateFactory
//import com.google.android.gms.maps.GoogleMap
//import com.google.android.gms.maps.OnMapReadyCallback
//import com.google.android.gms.maps.SupportMapFragment
//import com.google.android.gms.maps.model.LatLng
//import com.google.android.gms.maps.model.MarkerOptions
//
//import java.util.*
//
//
//class HomeFragment
//    : AppCompatActivity(),
//    OnMapReadyCallback
//{
//    private lateinit var map: GoogleMap
//    private lateinit var geocoder: Geocoder
//    private var locationPermissionGranted = false
//    private lateinit var binding: ContentMainBinding
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//
//        super.onCreate(savedInstanceState)
//        setContentView(activityMainBinding.root)
//        setSupportActionBar(activityMainBinding.toolbar)
//        binding =
//
//        checkGooglePlayServices()
//        requestPermission()
//        // XXX Write me.
//
//        val mapFragment = supportFragmentManager
//            .findFragmentById(R.id.mapFrag) as SupportMapFragment
//        mapFragment.getMapAsync(this)
//
////        (supportFragmentManager.findFragmentById(R.id.mapFrag) as SupportMapFragment?)?.let {
////            it.getMapAsync(this)
////        }
//
//
//        //init Geocode object
//        geocoder = Geocoder(this)
//
//        // This code is correct, but it assumes an EditText in your layout
//        // called mapET and a go button called goBut
//        binding.mapET.setOnEditorActionListener { /*v*/_, actionId, event ->
//            // If user has pressed enter, or if they hit the soft keyboard "send" button
//            // (which sends DONE because of the XML)
//            if ((event != null
//                        &&(event.action == KeyEvent.ACTION_DOWN)
//                        &&(event.keyCode == KeyEvent.KEYCODE_ENTER))
//                || (actionId == EditorInfo.IME_ACTION_DONE)) {
//                hideKeyboard()
//                binding.goBut.callOnClick()
//            }
//            false
//        }
//        binding.goBut.setOnClickListener{
//            //on go, read the EditText box and geocode that address to get a lat/long pair
//            //if the geocoding succeeds, use the first suggested address
//            val addr = binding.mapET.text.toString()
//            val loc = geocoder.getFromLocationName(addr, 1)[0]
//            //move the map’s camera to that location at zoom level 15.0f.
//            map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(loc.latitude, loc.longitude), 15.0f))
//        }
//
//    }
//    /**
//     * Manipulates the map once available.
//     * This callback is triggered when the map is ready to be used.
//     * This is where we can add markers or lines, add listeners or move the camera. In this case,
//     * we just add a marker near Sydney, Australia.
//     * If Google Play services is not installed on the device, the user will be prompted to install
//     * it inside the SupportMapFragment. This method will only be triggered once the user has
//     * installed Google Play services and returned to the app.
//     */
//    override fun onMapReady(googleMap: GoogleMap) {
//        map = googleMap
//        if( locationPermissionGranted ) {
//            // XXX Write me.
//            // Note, we checked location permissions in requestPermission, but the compiler
//            // might complain about our not checking it.
////
//            map.isMyLocationEnabled = true
//            map.uiSettings.isMyLocationButtonEnabled = true
//
//            map.setOnMapLongClickListener {
//                // clear markers
//                map.clear()
//            }
//
//            binding.cancelButton.setOnClickListener {
//                map.clear()
//            }
//
//            map.setOnMapClickListener {
//                //set marker at location
//                val roundLat = String.format("%.3f", it.latitude)
//                val roundLon = String.format("%.3f", it.longitude)
//                val finalStr = String.format("$roundLat,$roundLon")
//                map.addMarker(
//                    MarkerOptions()
//                        .position(it)
//                        .title(finalStr)
//                )
//            }
//        }
//
//        // XXX Write me.
//        // Start the map at the Harry Ransom center
//        val harryLoc = geocoder.getFromLocationName("Harry Ransom Center",1)[0]
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(harryLoc.latitude,harryLoc.longitude),15.0f))
//    }
//
//    // Everything below here is correct
//
//    // An Android nightmare
//    // https://stackoverflow.com/questions/1109022/close-hide-the-android-soft-keyboard
//    // https://stackoverflow.com/questions/7789514/how-to-get-activitys-windowtoken-without-view
//    private fun hideKeyboard() {
//        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromWindow(window.decorView.rootView.windowToken, 0);
//    }
//
//    private fun checkGooglePlayServices() {
//        val googleApiAvailability = GoogleApiAvailability.getInstance()
//        val resultCode =
//            googleApiAvailability.isGooglePlayServicesAvailable(this)
//        if (resultCode != ConnectionResult.SUCCESS) {
//            if (googleApiAvailability.isUserResolvableError(resultCode)) {
//                googleApiAvailability.getErrorDialog(this, resultCode, 257)?.show()
//            } else {
//                Log.i(javaClass.simpleName,
//                    "This device must install Google Play Services.")
//                finish()
//            }
//        }
//    }
//
//    private fun requestPermission() {
//        val locationPermissionRequest = registerForActivityResult(
//            ActivityResultContracts.RequestMultiplePermissions()
//        ) { permissions ->
//            when {
//                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
//                    locationPermissionGranted = true;
//                } else -> {
//                Toast.makeText(this,
//                    "Unable to show location - permission required",
//                    Toast.LENGTH_LONG).show()
//            }
//            }
//        }
//        locationPermissionRequest.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))
//    }
//}
//
*/ //uh


class HomeFragment : Fragment() {
    private lateinit var _binding : MapFragmentBinding
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Initialize view
        _binding = MapFragmentBinding.inflate(inflater,container,false)
        //val view: View = inflater.inflate(, container, false)

        // Initialize map fragment
        val supportMapFragment =
            childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment

        // Async map
        supportMapFragment.getMapAsync { googleMap ->

            googleMap.setOnMapClickListener { latLng -> // When clicked on map
                // Initialize marker options
                val markerOptions = MarkerOptions()

                // Set position of marker
                markerOptions.position(latLng)

                // Set title of marker
                markerOptions.title(latLng.latitude.toString() + " : " + latLng.longitude)

                // Remove all marker
                googleMap.clear()

                // Animating to zoom the marker
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))

                // Add marker on map
                googleMap.addMarker(markerOptions)
            }
        }
        // Return view
        return binding.root
    }

    fun observeSelectedArea(): MutableLiveData<String> {
        return selectedArea
    }

}
