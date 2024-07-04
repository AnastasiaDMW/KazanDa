package ru.itis.kazanda.fragments.profile

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import ru.itis.kazanda.R
import ru.itis.kazanda.databinding.FragmentEditScreenBinding


class EditScreenFragment : Fragment(R.layout.fragment_edit_screen) {

    private var binding: FragmentEditScreenBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEditScreenBinding.bind(view)

        val pref = context?.getSharedPreferences("Default", Context.MODE_PRIVATE)

        binding?.apply {

            confirmButton.setOnClickListener {
                if (etEmail.text.isNullOrEmpty() || etName.text.isNullOrEmpty()) {
                    Snackbar.make(root, "Некорректный E-mail или имя пользователя", Snackbar.LENGTH_LONG).show()
                } else {
                    pref?.edit {
                        putString(ARG_NAME, etName.text.toString())
                        putString(ARG_EMAIL, etEmail.text.toString())
                    }
                    findNavController().navigate(R.id.action_editScreenFragment_to_profileScreenFragment)
                }
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