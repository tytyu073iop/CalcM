package com.example.calcm

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class IntegrationTests {
    @Test
    fun testFullCalculationFlow() {
        // Step 1: Validate input
        val initialSum = "10000"
        val annualRate = "5"
        val years = 10
        val capitalization = Capitalization.MONTHLY

        val sum = Validation.validateInitialSum(initialSum)
        val rate = Validation.validateAnnualRate(annualRate)

        assertNotNull(sum)
        assertNotNull(rate)

        // Step 2: Calculate
        val result = FinancialCalculator.calculate(sum, rate, years, capitalization)

        // Step 3: Verify results
        assertNotNull(result)
        assertTrue(result.finalAmount > 10000.0)
        assertTrue(result.totalProfit > 0.0)
        assertEquals(10 * 12 + 1, result.history.size) // 10 years * 12 months + initial point
    }

    @Test
    fun testErrorFlow() {
        val initialSum = "invalid"
        val annualRate = "5"
        
        val sum = Validation.validateInitialSum(initialSum)
        assertNull(sum)
    }
}

fun assertTrue(condition: Boolean) {
    kotlin.test.assertTrue(condition)
}
