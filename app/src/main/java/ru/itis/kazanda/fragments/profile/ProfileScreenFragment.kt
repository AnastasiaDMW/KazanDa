package ru.itis.kazanda.fragments.profile

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import ru.itis.kazanda.R
import ru.itis.kazanda.databinding.FragmentProfileScreenBinding
import ru.itis.kazanda.fragments.main.PlaceRepository
import java.io.File


class ProfileScreenFragment : Fragment(R.layout.fragment_profile_screen) {

    private var binding: FragmentProfileScreenBinding? = null
    private var adapter: FavoritePlaceAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileScreenBinding.bind(view)

        val pref = context?.getSharedPreferences("Default", Context.MODE_PRIVATE)
        val filesDir = requireContext().filesDir
        val imagePath = File(filesDir, "image.png")

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
            if (imagePath.exists()) {
                Glide.with(requireContext())
                    .load(Uri.fromFile(imagePath))
                    .circleCrop()
                    .into(ivAvatar)
                ivIcon.setVisibility(View.INVISIBLE)
            }
            ivEdit.setOnClickListener {
                findNavController().navigate(R.id.action_profileScreenFragment_to_editScreenFragment)
            }
        }

        initAdapter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initAdapter() {
        binding?.apply {
            adapter = FavoritePlaceAdapter(
                list = PlaceRepository.places,
                glide = Glide.with(this@ProfileScreenFragment),
                onClick = {
                    val bundle = Bundle().apply {
                        putInt("placeId", it.id)
                    }
                    findNavController().navigate(
                        R.id.action_profileScreenFragment_to_detailScreenFragment,
                        bundle
                    )
                }
            )
            rvFavorite.adapter = adapter
            rvFavorite.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    companion object {
        private const val ARG_NAME = "ARG_NAME"
        private const val ARG_EMAIL = "ARG_EMAIL"
    }
}