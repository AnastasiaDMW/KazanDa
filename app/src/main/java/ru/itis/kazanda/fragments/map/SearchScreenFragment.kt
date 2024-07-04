package ru.itis.kazanda.fragments.map

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.itis.kazanda.R
import ru.itis.kazanda.databinding.FragmentSearchScreenBinding

class SearchScreenFragment : Fragment(R.layout.fragment_search_screen) {

    private var binding: FragmentSearchScreenBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchScreenBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}