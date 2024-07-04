package ru.itis.kazanda.fragments.profile

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.itis.kazanda.R
import ru.itis.kazanda.databinding.FragmentProfileScreenBinding

class ProfileScreenFragment : Fragment(R.layout.fragment_profile_screen) {

    private var binding: FragmentProfileScreenBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileScreenBinding.bind(view)

        val pref = context?.getSharedPreferences("Default", Context.MODE_PRIVATE)

        binding?.apply {
            pref?.getString(ARG_NAME, "Вася Пупкин")
                ?.takeIf { it.isNotEmpty() }
                ?.let {
                    tvName.text = it
                }
            pref?.getString(ARG_EMAIL, "неизвестно")
                ?.takeIf { it.isNotEmpty() }
                ?.let {
                    tvEmail.text = it
                }

            ivEdit.setOnClickListener {
                findNavController().navigate(R.id.action_profileScreenFragment_to_editScreenFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        private const val ARG_NAME = "ARG_NAME"
        private const val ARG_EMAIL = "ARG_EMAIL"
    }
}