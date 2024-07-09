package ru.itis.kazanda.fragments.map

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.itis.kazanda.R
import ru.itis.kazanda.databinding.FragmentSearchScreenBinding

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
            btnSearch.setOnClickListener {
                if (etPrice.text.isNullOrBlank()) {
                    findNavController().navigate(
                        resId = R.id.action_searchScreenFragment_to_mapScreenFragment,
                        args = MapScreenFragment.bundle(
                            categoryType = startMapViewModel!!.iconTexts[sCategory.selectedItemPosition].title,
                            price = 0
                        )
                    )
                } else {
                    findNavController().navigate(
                        resId = R.id.action_searchScreenFragment_to_mapScreenFragment,
                        args = MapScreenFragment.bundle(
                            categoryType = startMapViewModel!!.iconTexts[sCategory.selectedItemPosition].title,
                            price = etPrice.text.toString().toInt()
                        )
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}