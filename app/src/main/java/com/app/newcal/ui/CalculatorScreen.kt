package com.app.newcal.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.newcal.viewmodel.CalculatorViewModel

@Composable
fun CalculatorScreen(viewModel: CalculatorViewModel) {
    val expression = viewModel.expression.collectAsState()
    val result = viewModel.result.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = expression.value,
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.align(Alignment.End)
        )
        Text(
            text = result.value,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.align(Alignment.End)
        )
        Spacer(modifier = Modifier.height(24.dp))
        val buttons = listOf(
            listOf("C", "DEL", "/", "*"),
            listOf("7", "8", "9", "-"),
            listOf("4", "5", "6", "+"),
            listOf("1", "2", "3", "="),
            listOf("0", ".")
        )
        buttons.forEach { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                row.forEach { label ->
                    val weight = if (label == "0") 2f else 1f
                    ButtonItem(
                        label = label,
                        onClick = {
                            when (label) {
                                "C" -> viewModel.onClear()
                                "DEL" -> viewModel.onDelete()
                                "=" -> viewModel.onEquals()
                                "/", "*", "-", "+" -> viewModel.onOperatorClick(label)
                                else -> viewModel.onNumberClick(label)
                            }
                        },
                        weight = weight
                    )
                }
                if (row.size < 4) {
                    repeat(4 - row.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
fun ButtonItem(label: String, onClick: () -> Unit, weight: Float = 1f) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .weight(weight)
            .aspectRatio(1f)
            .background(
                color = if (label in listOf("C", "DEL", "/", "*", "-", "+", "=")) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(onClick = onClick)
    ) {
        Text(
            text = label,
            color = Color.White,
            fontSize = 24.sp
        )
    }
}
