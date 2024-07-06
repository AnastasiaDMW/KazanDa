package ru.itis.kazanda.fragments.main

enum class PaymentType(val amount: Int) {
    FREE(0){
        override fun toString(): String = "free"
    },
    STANDARD(1) {
        override fun toString() = "$"
    },
    PREMIUM(2) {
        override fun toString() = "$$"
    },
    VIP(3) {
        override fun toString() = "$$$"
    };

    abstract override fun toString(): String
}