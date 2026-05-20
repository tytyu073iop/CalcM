package com.example.calcm

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
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
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
actual fun YearSelector(
    years: Int,
    onYearsChange: (Int) -> Unit,
    modifier: Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text("Years: $years")
        Slider(
            value = years.toFloat(),
            onValueChange = { onYearsChange(it.toInt()) },
            valueRange = 1f..50f,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
actual fun FinancialChart(
    points: List<ChartPoint>,
    modifier: Modifier
) {
    if (points.isEmpty()) return

    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.secondary

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

        // Fill with gradient
        val fillPath = Path().apply {
            addPath(path)
            lineTo(width, height)
            lineTo(0f, height)
            close()
        }

        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(primaryColor.copy(alpha = 0.5f), Color.Transparent)
            )
        )

        drawPath(
            path = path,
            color = primaryColor,
            style = Stroke(width = 3.dp.toPx())
        )
    }
}
