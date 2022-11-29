package com.example.cookingntraveler

import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
    private lateinit var selectedArea: MutableLiveData<String>


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
        selectedArea = MutableLiveData()
        val supportMapFragment =
            childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment
        // Async map
        supportMapFragment.getMapAsync { googleMap ->

            googleMap.setOnMapClickListener { latLng -> // When clicked on map
                // Initialize marker options
                val markerOptions = MarkerOptions()

                // Set position of marker
                markerOptions.position(latLng)

                // Remove all marker
                googleMap.clear()

                // Animating to zoom the marker
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5f))

                // Add marker on map
                var countryName = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)[0].countryName
                markerOptions.title("Cooking up some recipes from ${countryName}")
                googleMap.addMarker(markerOptions)

                googleMap.uiSettings.isZoomControlsEnabled = true
                googleMap.uiSettings.isZoomGesturesEnabled = true


                Log.d("XXX", "Country: ${countryName}")
                selectedArea.value = countryName
            }
        }
    }

    fun observeSelectedArea(): MutableLiveData<String> {


        return selectedArea
    }
}