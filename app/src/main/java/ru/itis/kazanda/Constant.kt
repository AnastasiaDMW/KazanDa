package ru.itis.kazanda

import ru.itis.kazanda.data.CategoryType

object Constant {

    val categoryList = listOf(
        CategoryType(id = 0, title = "Еда"),
        CategoryType(id = 1, title = "Активный отдых"),
        CategoryType(id = 2, title = "Покупки")
    )
    const val CATEGORY_TYPE = "CATEGORY_TYPE"
    const val PRICE = "PRCIE"
}