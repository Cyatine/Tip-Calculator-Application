package com.quandrants.dawa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import com.quandrants.dawa.ui.theme.DawaTheme
import kotlin.math.ceil

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DawaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TipCalculatorApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun TipCalculatorApp(modifier: Modifier = Modifier) {
    var billAmount by remember { mutableStateOf("") }
    var tipPercentage by remember { mutableStateOf("") }
    var roundUp by remember { mutableStateOf( false) }

    val tip = calculateTip(billAmount, tipPercentage, roundUp)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Calculate Tip",
            style = MaterialTheme.typography.headlineSmall
        )

        // Bill Amount TextField
        TextField(
            value = billAmount,
            onValueChange = { billAmount = it },
            label = { Text("Bill Amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp) // Set height for consistent appearance
        )

        // Tip Percentage TextField
        TextField(
            value = tipPercentage,
            onValueChange = { tipPercentage = it },
            label = { Text("Tip Percentage") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp) // Set height for consistent appearance
        )

        // Round up switch
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp) // Set height for consistent appearance
                .padding(vertical = 8.dp), // Add padding for better spacing
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Round up tip?")
            Switch(
                checked = roundUp,
                onCheckedChange = { roundUp = it }
            )
        }

        // Display the calculated Tip Amount
        Text(
            text = "Tip Amount: $${"%.2f".format(tip)}",
            fontSize = 24.sp,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

fun calculateTip(amount: String, percentage: String, roundUp: Boolean): Double {
    val bill = amount.toDoubleOrNull() ?: 0.0
    val tipPercent = percentage.toDoubleOrNull() ?: 0.0
    var tip = bill * (tipPercent / 100)
    if (roundUp) {
        tip = ceil(tip)
    }
    return tip
}

@Preview(showBackground = true)
@Composable
fun TipCalculatorAppPreview() {
    DawaTheme {
        TipCalculatorApp()
    }
}