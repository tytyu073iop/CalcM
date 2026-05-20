package com.example.calcm

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
actual fun PlatformNumericInput(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier
) {
    // Simulating SpinBox with Buttons
    Row(
        modifier = modifier.width(300.dp).padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, modifier = Modifier.weight(1f))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.width(100.dp),
            singleLine = true
        )
        Column {
            IconButton(onClick = { 
                val current = value.toDoubleOrNull() ?: 0.0
                onValueChange((current + 1).toString())
            }, modifier = Modifier.size(24.dp)) {
                Text("+")
            }
            IconButton(onClick = { 
                val current = value.toDoubleOrNull() ?: 0.0
                onValueChange((current - 1).coerceAtLeast(0.0).toString())
            }, modifier = Modifier.size(24.dp)) {
                Text("-")
            }
        }
    }
}

@Composable
actual fun YearSelector(
    years: Int,
    onYearsChange: (Int) -> Unit,
    modifier: Modifier
) {
    PlatformNumericInput(
        label = "Years",
        value = years.toString(),
        onValueChange = { onYearsChange(it.toIntOrNull() ?: years) },
        modifier = modifier
    )
}

@Composable
actual fun FinancialChart(
    points: List<ChartPoint>,
    modifier: Modifier
) {
    if (points.isEmpty()) return

    Canvas(modifier = modifier.width(500.dp).height(300.dp).padding(16.dp)) {
        val width = size.width
        val height = size.height

        val maxAmount = points.maxOf { it.amount }
        val minAmount = points.minOf { it.amount }
        val range = if (maxAmount == minAmount) 1.0 else maxAmount - minAmount
        val maxYear = points.maxOf { it.year }

        val path = Path().apply {
            points.forEachIndexed { index, point ->
                val x = (point.year / maxYear).toFloat() * width
                val y = height - ((point.amount - minAmount) / range).toFloat() * height
                if (index == 0) moveTo(x, y) else lineTo(x, y)
            }
        }

        // Simple graphics without gradients
        drawPath(
            path = path,
            color = Color.Black,
            style = Stroke(width = 2.dp.toPx())
        )
    }
}
