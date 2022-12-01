package com.example.cookingntraveler

import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.cookingntraveler.databinding.FragmentMapBinding
import com.example.cookingntraveler.databinding.RecipesFragmentBinding
import com.example.cookingntraveler.databinding.SingleRecipeBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions

class SingleRecipeFragment : DialogFragment() {

    private var _binding: SingleRecipeBinding? = null

    private val binding get() = _binding!!
    var leaveDialog = MutableLiveData<Boolean>().default(false)


    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Initialize view
        // og approach
        super.onCreate(savedInstanceState);


        _binding = SingleRecipeBinding.inflate(inflater, container, false) //causing error
        // Return view
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initialize map fragment
        viewModel.netRandomRecipe()
        viewModel.observeSingleRecipe().observe(viewLifecycleOwner) {
            binding.name.text = it.strMeal
            Glide.with(this.requireContext().applicationContext)
                .load(it.strMealThumb)
                .placeholder(com.example.cookingntraveler.R.drawable.ingredients)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.recipePic)
            binding.instructions.text = it.strInstructions
        }

        binding.name.setOnClickListener {
            leaveDialog.value = true
        }
    }

    fun observeLeaveDialog(): MutableLiveData<Boolean> {
        return leaveDialog
    }

    fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }

}
