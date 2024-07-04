package ru.itis.kazanda.fragments.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.itis.kazanda.R
import ru.itis.kazanda.databinding.FragmentDetailScreenBinding

class DetailScreenFragment : Fragment(R.layout.fragment_detail_screen) {

    private var binding: FragmentDetailScreenBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailScreenBinding.bind(view)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}