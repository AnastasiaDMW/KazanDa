package ru.itis.kazanda.fragments.main

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import ru.itis.kazanda.R
import ru.itis.kazanda.databinding.FragmentMainScreenBinding

class MainScreenFragment : Fragment(R.layout.fragment_main_screen) {
    private var binding: FragmentMainScreenBinding? = null
    private lateinit var adapter: PlaceAdapter
    private lateinit var viewModel: MainViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainScreenBinding.bind(view)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        initAdapter()
        setupSearchView()
        binding?.filterButton?.setOnClickListener {
            showFilterDialog()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initAdapter() {
        adapter = PlaceAdapter(
            glide = Glide.with(this@MainScreenFragment),
            viewModel = viewModel,
            lifecycleOwner = viewLifecycleOwner,
            onClick = { place ->
                val bundle = Bundle().apply {
                    putInt("placeId", place.id)
                }
                findNavController().navigate(
                    R.id.action_mainScreenFragment_to_detailScreenFragment,
                    bundle
                )
            }
        )
        viewModel.places.observe(viewLifecycleOwner) { places ->
            adapter.submitList(places)
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
    private fun showFilterDialog() {
        val builder = AlertDialog.Builder(requireContext(), R.style.RoundedDialog)
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_price_range, null)
        val minPriceEditText = dialogView.findViewById<EditText>(R.id.minPriceEditText)
        val maxPriceEditText = dialogView.findViewById<EditText>(R.id.maxPriceEditText)

        builder.setView(dialogView)
            .setTitle("Введите диапазон оплаты")
            .setPositiveButton("Применить") { dialog, _ ->
                val minCost = minPriceEditText.text.toString().toIntOrNull() ?: 0
                val maxCost = maxPriceEditText.text.toString().toIntOrNull() ?: Int.MAX_VALUE
                if (minCost <= maxCost) {
                    adapter.filterByPriceRange(minCost, maxCost)
                } else {
                    Toast.makeText(requireContext(), "Минимальная цена должна быть меньше максимальной.", Toast.LENGTH_LONG).show()
                }
                dialog.dismiss()
            }
            .setNegativeButton("Отмена") { dialog, _ ->
                dialog.dismiss()
            }
        builder.create().show()
    }
}