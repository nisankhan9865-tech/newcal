package com.app.newcal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.app.newcal.ui.CalculatorScreen
import com.app.newcal.ui.theme.NewcalTheme
import com.app.newcal.viewmodel.CalculatorViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: CalculatorViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewcalTheme {
                CalculatorScreen(viewModel = viewModel)
            }
        }
    }
}
