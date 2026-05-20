package com.example.calcm

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun PlatformNumericInput(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
)

@Composable
expect fun YearSelector(
    years: Int,
    onYearsChange: (Int) -> Unit,
    modifier: Modifier = Modifier
)

@Composable
expect fun FinancialChart(
    points: List<ChartPoint>,
    modifier: Modifier = Modifier
)
