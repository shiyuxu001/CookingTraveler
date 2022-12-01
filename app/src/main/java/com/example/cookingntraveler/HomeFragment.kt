package com.example.cookingntraveler

import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import com.example.cookingntraveler.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class HomeFragment : Fragment() {
    private lateinit var _binding: FragmentMapBinding

    private val binding get() = _binding!!

    private lateinit var geocoder: Geocoder
    private val viewModel: MainViewModel by viewModels()
    private var selectedArea = MutableLiveData<String>().default("Starting")
    private var inputtedCountry = MutableLiveData<String>().default("Starting")
    private var invalidCountryClickIndicator = MutableLiveData<Boolean>().default(false)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Initialize view
        // og approach
        _binding = FragmentMapBinding.inflate(inflater, container, false) //causing error

        // Return view
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initialize map fragment
        geocoder = Geocoder(context)

        binding.completeSearchBut.setOnClickListener{
            val inputtedVal = binding.inputET.text.toString()
            binding.inputET.text.clear()
            inputtedCountry.value = viewModel.convertCountry(inputtedVal.capitalizeWords())
        }

        val supportMapFragment =
            childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment
        // Async map
        supportMapFragment.getMapAsync { googleMap ->

            googleMap.setOnMapClickListener { latLng -> // When clicked on map
                // Initialize marker options

                // Initialize marker options
                val markerOptions = MarkerOptions()

                // Set position of marker
                markerOptions.position(latLng)

                // Remove all marker
                googleMap.clear()
                val clicked= geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)

                if(clicked !=null && clicked.size > 0 && clicked[0].countryName != null){

                    // Animating to zoom the marker
                    googleMap.addMarker(markerOptions)
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 4f))
                    markerOptions.title(clicked[0].countryName)

                    Log.d("XXX", "Country: ${clicked[0].countryName}")
                    selectedArea.value = clicked[0].countryName
                }else{
                    //not a country
                    invalidCountryClickIndicator.value = true
                    // tells main acitivity to make the toast

                }

                googleMap.uiSettings.isZoomControlsEnabled = true
                googleMap.uiSettings.isZoomGesturesEnabled = true
            }
        }
    }

    fun resetInvalidCountryClickIndicator() {
        invalidCountryClickIndicator = MutableLiveData<Boolean>().default(false)
    }

    fun observeSelectedArea(): MutableLiveData<String> {
        return selectedArea
    }

    fun observeClickIndicator(): MutableLiveData<Boolean> {
        return invalidCountryClickIndicator
    }

    fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }

    fun observeInputtedCountry(): MutableLiveData<String> {
        return inputtedCountry
    }

    fun String.capitalizeWords(): String = split(" ").map { it.toLowerCase().capitalize() }.joinToString(" ")


}