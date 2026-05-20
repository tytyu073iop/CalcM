package com.example.calcm

import kotlin.math.pow

enum class Capitalization(val periodsPerYear: Int) {
    MONTHLY(12),
    QUARTERLY(4),
    ANNUALLY(1)
}

data class CalculationResult(
    val finalAmount: Double,
    val totalProfit: Double,
    val history: List<ChartPoint>
)

data class ChartPoint(
    val year: Double,
    val amount: Double
)

object FinancialCalculator {
    fun calculate(
        initialSum: Double,
        annualRate: Double,
        years: Int,
        capitalization: Capitalization
    ): CalculationResult {
        val r = annualRate / 100.0
        val n = capitalization.periodsPerYear
        
        val history = mutableListOf<ChartPoint>()
        
        // Add initial point
        history.add(ChartPoint(0.0, initialSum))
        
        // Calculate for each period to build history
        // Total periods = n * years
        val totalPeriods = n * years
        for (p in 1..totalPeriods) {
            val t = p.toDouble() / n
            val amount = initialSum * (1 + r / n).pow(n * t)
            
            // We only add points for years to keep it simple or for all periods if needed
            // Let's add all periods for smoother chart
            history.add(ChartPoint(t, amount))
        }

        val finalAmount = history.last().amount
        val totalProfit = finalAmount - initialSum

        return CalculationResult(
            finalAmount = finalAmount,
            totalProfit = totalProfit,
            history = history
        )
    }
}
