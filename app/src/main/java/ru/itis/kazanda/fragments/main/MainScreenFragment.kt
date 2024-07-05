package ru.itis.kazanda.fragments.main

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import ru.itis.kazanda.R
import ru.itis.kazanda.databinding.FragmentMainScreenBinding

class MainScreenFragment : Fragment(R.layout.fragment_main_screen) {
    private var binding: FragmentMainScreenBinding? = null
    private lateinit var adapter: PlaceAdapter
    private var places: List<Place> = listOf()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainScreenBinding.bind(view)
        places = PlaceRepository.places
        initAdapter()
        setupSearchView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initAdapter() {
        adapter = PlaceAdapter(Glide.with(this@MainScreenFragment)) { place ->
            val bundle = Bundle().apply {
                putInt("placeId", place.id)
            }
            findNavController().navigate(R.id.action_mainScreenFragment_to_detailScreenFragment, bundle)
        }.apply {
            submitList(places)
        }

        binding?.placesRecyclerView?.apply {
            this.adapter = this@MainScreenFragment.adapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }
    private fun setupSearchView() {
        binding?.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    adapter?.filterPlaces(it)
                }
                return true
            }
        })
    }
}