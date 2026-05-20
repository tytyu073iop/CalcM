package com.example.calcm

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import calcm.shared.generated.resources.*
import kotlin.math.pow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    MaterialTheme {
        var initialSum by remember { mutableStateOf("10000") }
        var annualRate by remember { mutableStateOf("5") }
        var years by remember { mutableStateOf(10) }
        var capitalization by remember { mutableStateOf(Capitalization.MONTHLY) }
        
        var result by remember { mutableStateOf<CalculationResult?>(null) }
        var errorMessage by remember { mutableStateOf<String?>(null) }

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(title = { Text(stringResource(Res.string.app_name)) })
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                PlatformNumericInput(
                    label = stringResource(Res.string.initial_sum),
                    value = initialSum,
                    onValueChange = { initialSum = it }
                )

                PlatformNumericInput(
                    label = stringResource(Res.string.annual_rate),
                    value = annualRate,
                    onValueChange = { annualRate = it }
                )

                YearSelector(
                    years = years,
                    onYearsChange = { years = it }
                )

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(stringResource(Res.string.capitalization), style = MaterialTheme.typography.labelSmall)
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Capitalization.entries.forEach { cap ->
                            FilterChip(
                                selected = capitalization == cap,
                                onClick = { capitalization = cap },
                                label = {
                                    Text(when(cap) {
                                        Capitalization.MONTHLY -> stringResource(Res.string.monthly)
                                        Capitalization.QUARTERLY -> stringResource(Res.string.quarterly)
                                        Capitalization.ANNUALLY -> stringResource(Res.string.annually)
                                    })
                                }
                            )
                        }
                    }
                }

                Button(
                    onClick = {
                        val sum = Validation.validateInitialSum(initialSum)
                        val rate = Validation.validateAnnualRate(annualRate)
                        
                        if (sum != null && rate != null) {
                            result = FinancialCalculator.calculate(sum, rate, years, capitalization)
                            errorMessage = null
                        } else {
                            errorMessage = "Invalid input"
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(Res.string.calculate))
                }

                errorMessage?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }

                result?.let { res ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("${stringResource(Res.string.final_amount)}: ${res.finalAmount.toFixed(2)}")
                            Text("${stringResource(Res.string.total_profit)}: ${res.totalProfit.toFixed(2)}")
                        }
                    }
                    
                    FinancialChart(
                        points = res.history,
                        modifier = Modifier.fillMaxWidth().height(300.dp)
                    )
                }
            }
        }
    }
}

fun Double.toFixed(digits: Int): String {
    val factor = 10.0.pow(digits)
    val rounded = (this * factor).toLong() / factor
    val s = rounded.toString()
    if (digits > 0 && !s.contains(".")) {
        return s + "." + "0".repeat(digits)
    }
    val parts = s.split(".")
    if (parts.size == 2 && parts[1].length < digits) {
        return s + "0".repeat(digits - parts[1].length)
    }
    return s
}
