package com.example.shoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppinglist.ui.theme.ShoppingListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShoppingListTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ShoppingList(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ShoppingList(modifier: Modifier = Modifier) {
    var inputName by remember { mutableStateOf(TextFieldValue("")) }
    var inputQuantity by remember { mutableStateOf(TextFieldValue("")) }
    var shoppingList by remember { mutableStateOf(listOf<String>()) }
    var quantityList by remember { mutableStateOf(listOf<String>()) }
    var selectedUnit by remember { mutableStateOf("") }

    val onSelectedChange: ((String) -> Unit) = { unit ->
        selectedUnit = unit
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 30.dp)
        ) {
            TextField(
                value = inputName,
                onValueChange = { inputName = it },
                label = { Text("Item Name") },
                modifier = Modifier.fillMaxWidth()

            )
            TextField(
                value = inputQuantity,
                onValueChange = { inputQuantity = it },
                label = { Text("Item Quantity") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            )
            UnitSelectionRadioButton(selectedUnit, onSelectedChange)
            Spacer(modifier = Modifier.height(5.dp))
            Button(
                onClick = {
                        if (inputName.text.isNotBlank() && inputQuantity.text.isNotBlank()) {
                            shoppingList = shoppingList + inputName.text
                            quantityList = quantityList + (inputQuantity.text + " " + selectedUnit)
                            inputName = TextFieldValue("")
                            inputQuantity = TextFieldValue("")
                            selectedUnit = ""
                        }
                },
            ) {
                Text("Add Item")
            }
        }
        LazyColumn (modifier = Modifier.padding(top = 15.dp)) {
            itemsIndexed(shoppingList) { index, name ->
                var checked by remember { mutableStateOf(false) }

                Card (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                ) {
                    Row (verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = checked,
                            onCheckedChange = {checked = it }
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = name,
                                fontSize = 20.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = quantityList[index],
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun UnitSelectionRadioButton(selected: String, onSelectedChange: (String) -> Unit) {
    val units = listOf("items", "lb", "gal", "oz", "fl oz")
    Row {
        units.forEach { unit ->
            Row(verticalAlignment = Alignment.CenterVertically){
                RadioButton(
                    selected = (unit == selected),
                    onClick = { onSelectedChange(unit) }
                )
                Text(
                    text = unit
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ShoppingListTheme {
        ShoppingList()
    }
}