package com.example.cookingntraveler

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookingntraveler.databinding.RecipesFragmentBinding
import java.util.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class RecipesFragment() : Fragment() {

    private var _binding: RecipesFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
    var selectedCategories: MutableList<String> = arrayListOf()
    var backButtonPressed = MutableLiveData<Boolean>().default(false)
    var communicateWithMain = MutableLiveData<Boolean>().default(false)
    var sendMainMealId = MutableLiveData<Long>().default(0)


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
        initRecyclerViewDividers(binding.recyclerView)

        listAdapter.observeCreatePopUp().observe(viewLifecycleOwner) {
            communicateWithMain.value = true
        }
        listAdapter.observeMealId().observe(viewLifecycleOwner) {
            sendMainMealId.value = it
        }

        binding.backButton.setOnClickListener {
            backButtonPressed.value = true
        }

        viewModel.observeCategoryList().observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                val filterOptions = it.toMutableList()
                val selectedCategory = BooleanArray(filterOptions.size)
                val categoryList = ArrayList<Int>()
                binding.categoryFilterDropDown.setOnClickListener {
                    val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                    // set title
                    builder.setTitle("Filter recipes")

                    // set dialog non cancelable
                    builder.setCancelable(false)
                    builder.setMultiChoiceItems(filterOptions.toTypedArray(), selectedCategory) {
                         dialogInterface, i, b ->
                            // check condition
                            if (b) {
                                // when checkbox selected add position in list
                                categoryList.add(i)
                                // Sort array list
                                Collections.sort(categoryList)
                            } else {
                                // when checkbox unselected list
                                categoryList.remove(Integer.valueOf(i))
                            }
                        }
                    builder.setPositiveButton("OK",
                        DialogInterface.OnClickListener { dialogInterface, i -> // Initialize string builder
                            val stringBuilder = StringBuilder()
                            selectedCategories.clear()
                            // use for loop
                            for (j in 0 until categoryList.size) {
                                // concat array value
                                stringBuilder.append(filterOptions[categoryList[j]])
                                selectedCategories.add(filterOptions[categoryList[j]])
                                // check condition
                                if (j != (categoryList.size - 1)) {
                                    stringBuilder.append(", ")
                                }
                            }
                            // set text on textView
                            binding.categoryFilterDropDown.text = stringBuilder.toString()
                            val unSelectedFilters = createUnselectedList(filterOptions.toList(), selectedCategories)
                            if (unSelectedFilters.size == filterOptions.size) {
                                viewModel.netFilterCategory(emptyList<String>().toMutableList(), filterOptions)
                            } else {
                                viewModel.netFilterCategory(unSelectedFilters, filterOptions)
                            }
                        })
                    builder.setNegativeButton("Cancel",
                        DialogInterface.OnClickListener { dialogInterface, i -> // dismiss dialog
                            dialogInterface.dismiss()
                        })
                    builder.setNeutralButton("Clear All",
                        DialogInterface.OnClickListener { dialogInterface, i ->
                            // use for loop
                            for (j in selectedCategory.indices) {
                                // remove all selection
                                selectedCategory[j] = false
                                // clear language list
                                categoryList.clear()

                                viewModel.netFilterCategory(emptyList<String>().toMutableList(), filterOptions)
                                // clear text view value
                                binding.categoryFilterDropDown.text = "Filter Recipes"
                            }
                        })
                    // show dialog
                    builder.show()
                }
            }
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
    fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }


    fun observerBackButtonPushed(): MutableLiveData<Boolean> {
        return backButtonPressed
    }

    fun observeCommunicateWithMain(): MutableLiveData<Boolean> {
        return communicateWithMain
    }

    fun observeSendMainMealId() : MutableLiveData<Long> {
        return sendMainMealId
    }


}

private fun createUnselectedList(filterOptions : List<String>, selectedList: MutableList<String>) : MutableList<String>{
    val unselectedList = filterOptions.toMutableList()
    for (filter in filterOptions) {
        for (selected in selectedList) {
            if (filter.equals(selected)) {
                unselectedList.remove(selected)
            }
        }
    }
    return unselectedList
}


private fun initRecyclerViewDividers(rv: RecyclerView) {
    // Let's have dividers between list items
    val dividerItemDecoration = DividerItemDecoration(
        rv.context, LinearLayoutManager.VERTICAL )
    rv.addItemDecoration(dividerItemDecoration)
}
