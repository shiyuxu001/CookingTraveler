package com.example.cookingntraveler

import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

                // Initialize marker options
                val markerOptions = MarkerOptions()

                // Set position of marker
                markerOptions.position(latLng)

                // Remove all marker
                googleMap.clear()
                val clicked= geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)


//                if(clicked !=null && clicked[0].countryName != null && isValidCountry(clicked[0].countryName)){

                if(clicked !=null && clicked[0].countryName != null ){

                    // Animating to zoom the marker
                    googleMap.addMarker(markerOptions)
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 4f))
                    markerOptions.title(clicked[0].countryName)

                    //TODO: add a message or smth ?? on the marker

                    //TODO: delay

                    //Switch to new fragment of recipes

                    //TODO:
                    Log.d("XXX", "Country: ${clicked[0].countryName}")
                    selectedArea.value = clicked[0].countryName
                }else{
                    //not a country
                    //TODO: crashing !?
                    Toast.makeText(this.context, "Please click a valid country!",Toast.LENGTH_SHORT)

                }

                googleMap.uiSettings.isZoomControlsEnabled = true
                googleMap.uiSettings.isZoomGesturesEnabled = true

//
//                Log.d("XXX", "Country: ${countryName}")
//                selectedArea.value = countryName
            }
        }
    }

    fun observeSelectedArea(): MutableLiveData<String> {


        return selectedArea
    }
}