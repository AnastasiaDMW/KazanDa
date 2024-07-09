package ru.itis.kazanda.fragments.map

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.itis.kazanda.Constant.CATEGORY_TYPE
import ru.itis.kazanda.Constant.PRICE
import ru.itis.kazanda.R
import ru.itis.kazanda.databinding.FragmentMapScreenBinding

class MapScreenFragment : Fragment(R.layout.fragment_map_screen) {

    private var binding: FragmentMapScreenBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMapScreenBinding.bind(view)
    }

    companion object {
        fun bundle(categoryType: String, price: Int): Bundle = Bundle().apply {
            putString(CATEGORY_TYPE, categoryType)
            putInt(PRICE, price)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}