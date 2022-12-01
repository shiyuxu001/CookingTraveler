package com.example.cookingntraveler

import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
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
    companion object {
        const val titleKey = "recipeTitle"
        const val instrKey = "instructionTitle"
        const val thumbnailKey = "thumbnailTitle"
        fun newInstance(recipeName: String, instructions: String, thumbnail: String): SingleRecipeFragment {
            val frag = SingleRecipeFragment()
            val bundle = Bundle()
            bundle.putString(titleKey, recipeName)
            bundle.putString(instrKey, instructions)
            bundle.putString(thumbnailKey, thumbnail)

            frag.arguments = bundle
            return frag
        }
    }


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
            binding.singleTitle.text = requireArguments().getString(titleKey)
            Glide.with(this.requireContext().applicationContext)
                .load(requireArguments().getString(thumbnailKey))
                .placeholder(com.example.cookingntraveler.R.drawable.ingredients)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.singleImage)
            binding.singleInstructions.text = requireArguments().getString(instrKey)

        binding.backBtn.setOnClickListener {
            leaveDialog.value = true
        }
    }

    fun observeLeaveDialog(): MutableLiveData<Boolean> {
        return leaveDialog
    }

    fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }

}
