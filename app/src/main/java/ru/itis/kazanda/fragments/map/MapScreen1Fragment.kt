package ru.itis.kazanda.fragments.map

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.itis.kazanda.R
import ru.itis.kazanda.databinding.FragmentMapScreen1Binding

class MapScreen1Fragment : Fragment(R.layout.fragment_map_screen_1) {

    private var binding: FragmentMapScreen1Binding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMapScreen1Binding.bind(view)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}