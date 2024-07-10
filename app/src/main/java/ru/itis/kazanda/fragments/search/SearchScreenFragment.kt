package ru.itis.kazanda.fragments.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.itis.kazanda.R
import ru.itis.kazanda.databinding.FragmentSearchScreenBinding
import ru.itis.kazanda.fragments.map.MapScreenFragment

class SearchScreenFragment : Fragment(R.layout.fragment_search_screen) {

    private var binding: FragmentSearchScreenBinding? = null
    private var startMapViewModel: SearchViewModel? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchScreenBinding.bind(view)
        startMapViewModel = SearchViewModel()

        init()
    }

    private fun init() {

        binding?.run {
            sCategory.adapter = startMapViewModel?.iconTexts?.let { SpinnerAdapter(root.context, it) }
            val price = if (etPrice.text.isNullOrBlank()) 0 else etPrice.text.toString().toInt()
            btnSearch.setOnClickListener {
                findNavController().navigate(
                    resId = R.id.action_searchScreenFragment_to_mapScreenFragment,
                    args = MapScreenFragment.bundle(
                        categoryType = startMapViewModel!!.iconTexts[sCategory.selectedItemPosition].title,
                        price = price
                    )
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}