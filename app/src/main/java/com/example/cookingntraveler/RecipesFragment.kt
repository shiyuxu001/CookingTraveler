package com.example.cookingntraveler

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.example.cookingntraveler.databinding.RecipesFragmentBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class RecipesFragment : Fragment() {

    private var _binding: RecipesFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var categories = MutableLiveData<List<String>>().default(emptyList())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = RecipesFragmentBinding.inflate(inflater, container, false)
        return binding.root
        // TODO: initialize recycler view

    }

    // TODO: look into creating a pop-up when a row is clicked
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
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