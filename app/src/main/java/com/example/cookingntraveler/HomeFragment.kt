package com.example.cookingntraveler
import android.location.Geocoder
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.activityViewModels
import com.example.cookingntraveler.databinding.MapFragmentBinding

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import androidx.lifecycle.MutableLiveData
import com.example.cookingntraveler.databinding.FragmentMapBinding

class HomeFragment : Fragment() {
    private lateinit var _binding : MapFragmentBinding
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
//    private val mainActivity: MainActivity
    private lateinit var geocoder: Geocoder
    private lateinit var selectedArea: MutableLiveData<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Initialize view

        geocoder = Geocoder(this.context)
        _binding = MapFragmentBinding.inflate(inflater,container,false) //causing error
        selectedArea = MutableLiveData()
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

                selectedArea.value = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)[0].countryName

            }
        }
        // Return view
        return binding.root
    }

    fun observeSelectedArea(): MutableLiveData<String> {
        return selectedArea
    }

}