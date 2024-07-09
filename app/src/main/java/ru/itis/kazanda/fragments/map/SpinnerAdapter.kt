package ru.itis.kazanda.fragments.map

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import ru.itis.kazanda.R
import ru.itis.kazanda.data.Category
import ru.itis.kazanda.databinding.CustomSpinnerBinding

class SpinnerAdapter(
    private var context: Context,
    private var categories: List<Category>
): ArrayAdapter<Category>(context, 0, categories){

    private var binding: CustomSpinnerBinding? = null

    override fun getView(position: Int, recycledView: View?, parent: ViewGroup): View {
        return this.createView(position, recycledView, parent)
    }

    override fun getDropDownView(position: Int, recycledView: View?, parent: ViewGroup): View {
        return this.createView(position, recycledView, parent)
    }

    private fun createView(position: Int, recycledView: View?, parent: ViewGroup): View {

        val category = getItem(position)

        val view = recycledView ?: LayoutInflater.from(context).inflate(
            R.layout.custom_spinner,
            parent,
            false
        )
        binding = CustomSpinnerBinding.bind(view)
        binding?.run {
            if (category != null) {
                ivIcon.setImageDrawable(ContextCompat.getDrawable(root.context, category.icon))
                tvCategory.text = category.title
            }
        }

        return view
    }
}