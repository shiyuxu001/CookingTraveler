package com.example.cookingntraveler

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cookingntraveler.api.FilterRecipe
import com.example.cookingntraveler.databinding.RecipesFragmentBinding
import com.google.android.gms.maps.SupportMapFragment

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class RecipesFragment() : Fragment() {

    private var _binding: RecipesFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var categories = MutableLiveData<List<String>>().default(emptyList())
    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var listAdapter:RecipeRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        listAdapter = RecipeRVAdapter(this.requireContext())

        _binding = RecipesFragmentBinding.inflate(inflater, container, false)


        return binding.root
    }

    // TODO: look into creating a pop-up when a row is clicked
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(binding.recyclerView.context)
        binding.recyclerView.adapter = listAdapter
        listAdapter.submitList(viewModel.observeDisplayedList().value)

        binding.backButton.setOnClickListener {
//            parentFragmentManager.popBackStack() //will exit the app??

        }

        viewModel.observeDisplayedList().observe(viewLifecycleOwner) {
            //list to be displayed is changed
            listAdapter.submitList(it)
            listAdapter.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun observeCategories(): MutableLiveData<List<String>> {
        return categories
    }



    // TODO: when back is pressed getActivity().getFragmentManager().popBackStack();
    fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }

}