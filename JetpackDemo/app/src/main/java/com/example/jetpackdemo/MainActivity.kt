package com.example.jetpackdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "first") {
        composable("first") { FirstScreen(navController) }
        composable("second/{numberOfPersons}") { backStackEntry ->
            val numberOfPersons =
                backStackEntry.arguments?.getString("numberOfPersons")?.toInt() ?: 0
            SecondScreen(numberOfPersons)
        }
    }
}

@Composable
fun FirstScreen(navController: NavHostController) {
    var billAmount by remember { mutableStateOf(TextFieldValue("")) }
    var numberOfPersons by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = billAmount,
            onValueChange = { billAmount = it },
            label = { Text("Bill Amount") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = numberOfPersons,
            onValueChange = { numberOfPersons = it },
            label = { Text("Number of Persons") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (numberOfPersons.text.isNotEmpty()) {
                    navController.navigate("second/${numberOfPersons.text}")
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Next")
        }
    }
}

@Composable
fun SecondScreen(numberOfPersons: Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        var persons = listOf<Person>()

        for (i in 1..numberOfPersons) {
            NameAndAmount(i)
            persons.plus(Person())
        }
        Button(
            onClick = {
                /*TODO*/
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Next")
        }
    }
}

@Composable
fun NameAndAmount(n: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Person ${n + 1}") },
            modifier = Modifier.weight(1f)
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Amount") },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun ResultScreen() {
}

