package ru.itis.kazanda

import ru.itis.kazanda.data.CategoryType

object Constant {

    val categoryList = listOf(
        CategoryType(id = 0, title = "Еда"),
        CategoryType(id = 1, title = "Покупки"),
        CategoryType(id = 2, title = "Путешествия")
    )
    const val CATEGORY_TYPE = "CATEGORY_TYPE"
    const val PRICE = "PRCIE"
}