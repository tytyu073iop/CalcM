package com.example.calcm

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FinancialCalculatorTest {
    @Test
    fun testCompoundInterest() {
        val result = FinancialCalculator.calculate(1000.0, 5.0, 1, Capitalization.ANNUALLY)
        assertEquals(1050.0, result.finalAmount, 0.1)
        assertEquals(50.0, result.totalProfit, 0.1)
    }

    @Test
    fun testMonthlyCapitalization() {
        val result = FinancialCalculator.calculate(1000.0, 12.0, 1, Capitalization.MONTHLY)
        assertEquals(1126.82, result.finalAmount, 0.1)
    }

    @Test
    fun testQuarterlyCapitalization() {
        val result = FinancialCalculator.calculate(1000.0, 8.0, 1, Capitalization.QUARTERLY)
        assertEquals(1082.43, result.finalAmount, 0.1)
    }

    @Test
    fun testZeroRate() {
        val result = FinancialCalculator.calculate(1000.0, 0.0, 10, Capitalization.ANNUALLY)
        assertEquals(1000.0, result.finalAmount, 0.1)
        assertEquals(0.0, result.totalProfit, 0.1)
    }

    @Test
    fun testLargeValues() {
        val result = FinancialCalculator.calculate(1000000.0, 10.0, 30, Capitalization.ANNUALLY)
        assertEquals(17449402.26, result.finalAmount, 1.0)
    }

    @Test
    fun testValidation() {
        assertTrue(Validation.validateInitialSum("100") != null)
        assertTrue(Validation.validateInitialSum("-100") == null)
        assertTrue(Validation.validateInitialSum("abc") == null)
        
        assertTrue(Validation.validateAnnualRate("5.5") != null)
        assertTrue(Validation.validateAnnualRate("1500") == null)
        
        assertTrue(Validation.validateYears("10") != null)
        assertTrue(Validation.validateYears("150") == null)
    }
}
