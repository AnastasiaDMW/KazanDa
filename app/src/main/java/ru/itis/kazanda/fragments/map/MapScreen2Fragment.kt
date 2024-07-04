package ru.itis.kazanda.fragments.map

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.itis.kazanda.R
import ru.itis.kazanda.databinding.FragmentMapScreen2Binding

class MapScreen2Fragment : Fragment(R.layout.fragment_map_screen_2) {

    private var binding: FragmentMapScreen2Binding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMapScreen2Binding.bind(view)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}