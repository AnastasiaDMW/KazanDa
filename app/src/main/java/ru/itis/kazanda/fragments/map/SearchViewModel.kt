package ru.itis.kazanda.fragments.map

import androidx.lifecycle.ViewModel
import ru.itis.kazanda.R
import ru.itis.kazanda.data.Category

class SearchViewModel: ViewModel() {

    val iconTexts = listOf(
        Category(R.drawable.baseline_all_out_32, "Все"),
        Category(R.drawable.baseline_fastfood_32, "Еда"),
        Category(R.drawable.baseline_shopping_cart_32, "Покупки"),
        Category(R.drawable.baseline_travel_explore_32, "Путешествия")
    )

}