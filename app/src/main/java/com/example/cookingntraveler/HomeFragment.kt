package com.example.cookingntraveler

class HomeFragment {
}
//
//import android.graphics.Color
//import android.os.Build
//import android.os.Bundle
//import android.text.Html
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.TextView
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.activityViewModels
//import edu.cs371m.triviagame.MainViewModel
//import edu.cs371m.triviagame.api.TriviaQuestion
//import edu.cs371m.triviagame.databinding.MainFragmentBinding
//
//class MainFragment : Fragment() {
//    companion object {
//        const val idKey = "idKey"
//        fun newInstance(id: Int): MainFragment {
//            val b = Bundle()
//            b.putInt(idKey, id)
//            val frag = MainFragment()
//            frag.arguments = b
//            return frag
//        }
//    }
//    private var _binding : MainFragmentBinding? = null
//    // This property is only valid between onCreateView and onDestroyView
//    private val binding get() = _binding!!
//
//    private val viewModel: MainViewModel by activityViewModels()
//    // XXX initialize the viewModel
//
//    private fun setClickListeners(question: TriviaQuestion, tv: TextView, tb: Button, fb: Button) {
//        // XXX Write me Color.GREEN for correct, Color.RED for incorrect
//        resetQColor()
//        if (question.answer == "True") {
//            tb.setOnClickListener {
//                tv.setBackgroundColor(Color.GREEN)
//            }
//            fb.setOnClickListener {
//                tv.setBackgroundColor(Color.RED)
//            }
//        } else {
//            tb.setOnClickListener {
//                tv.setBackgroundColor(Color.RED)
//            }
//            fb.setOnClickListener {
//                tv.setBackgroundColor(Color.GREEN)
//            }
//        }
//    }
//    // Corrects some ugly HTML encodings
//    private fun fromHtml(source: String): String {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY).toString()
//        } else {
//            @Suppress("DEPRECATION")
//            return Html.fromHtml(source).toString()
//        }
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = MainFragmentBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        // XXX Write me.  viewModel should observe something
//        // When it gets what it is observing, it should index into it
//        // You might find the requireArguments() function useful
//        // You should let MainActivity know to "turn off" the swipe
//        // refresh spinner.
//        resetQColor()
//        viewModel.observeTriviaQuestion().observe(viewLifecycleOwner) {
//                questions -> binding.qTV.text = fromHtml(questions[requireArguments().getInt(idKey)].question)
//            setClickListeners(questions[requireArguments().getInt(idKey)], binding.qTV,
//                binding.qTrueBut, binding.qFalseBut)
//        }
//        viewModel.fetchDone.postValue(true)
//    }
//    private fun resetQColor() {
//        binding.qTV.setBackgroundColor(Color.TRANSPARENT)
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}
