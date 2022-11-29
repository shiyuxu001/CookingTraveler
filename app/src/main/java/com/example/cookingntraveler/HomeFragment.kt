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
        geocoder = Geocoder(activity, Locale.getDefault()) // TODO: hmmm

        // og approach
        _binding = FragmentMapBinding.inflate(inflater, container, false) //causing error
        selectedArea = MutableLiveData()

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

                // Remove all marker
                googleMap.clear()

                // Animating to zoom the marker
                // TODO: dont zoom the camera in this much
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))

                // Add marker on map
                googleMap.addMarker(markerOptions)

                googleMap.uiSettings.isZoomControlsEnabled = true
                googleMap.uiSettings.isZoomGesturesEnabled = true

                var countryName = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)[0].countryName
                markerOptions.title(countryName)
                Log.d("XXX", "Country: ${countryName}")
                selectedArea.value = countryName
            }
        }
        // Return view
        return binding.root
    }

    fun observeSelectedArea(): MutableLiveData<String> {
        return selectedArea
    }
}