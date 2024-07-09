package ru.itis.kazanda.data
sealed class StateResult {
    data class Success(val data: Any): StateResult()
    data class Error(val message: String): StateResult()
    data class Info(val message: String): StateResult()
    data object Loading: StateResult()
}