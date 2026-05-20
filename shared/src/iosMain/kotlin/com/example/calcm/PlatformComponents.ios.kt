package com.example.calcm

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.LightGray,
            focusedBorderColor = Color.Gray
        )
    )
}

@Composable
actual fun YearSelector(
    years: Int,
    onYearsChange: (Int) -> Unit,
    modifier: Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text("Select Years (iOS Picker style):", style = MaterialTheme.typography.labelSmall)
        LazyRow(
            modifier = Modifier.fillMaxWidth().height(50.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items((1..50).toList()) { year ->
                val isSelected = year == years
                Surface(
                    modifier = Modifier.clickable { onYearsChange(year) },
                    color = if (isSelected) Color.Gray else Color.Transparent,
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = year.toString(),
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        color = if (isSelected) Color.White else Color.Black
                    )
                }
            }
        }
    }
}

@Composable
actual fun FinancialChart(
    points: List<ChartPoint>,
    modifier: Modifier
) {
    if (points.isEmpty()) return

    Canvas(modifier = modifier.fillMaxWidth().height(200.dp).padding(16.dp)) {
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

        // Thin lines for iOS
        drawPath(
            path = path,
            color = Color.Blue,
            style = Stroke(width = 1.dp.toPx())
        )
    }
}
