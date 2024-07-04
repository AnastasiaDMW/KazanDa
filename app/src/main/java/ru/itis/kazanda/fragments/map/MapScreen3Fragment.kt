package ru.itis.kazanda.fragments.map

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.itis.kazanda.R
import ru.itis.kazanda.databinding.FragmentMapScreen3Binding

class MapScreen3Fragment : Fragment(R.layout.fragment_map_screen_3) {

    private var binding: FragmentMapScreen3Binding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMapScreen3Binding.bind(view)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}