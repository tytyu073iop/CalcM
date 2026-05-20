package com.example.calcm

object Validation {
    fun validateInitialSum(value: String): Double? {
        return value.toDoubleOrNull()?.takeIf { it >= 0 }
    }

    fun validateAnnualRate(value: String): Double? {
        return value.toDoubleOrNull()?.takeIf { it in 0.0..1000.0 }
    }

    fun validateYears(value: String): Int? {
        return value.toIntOrNull()?.takeIf { it in 0..100 }
    }
}
