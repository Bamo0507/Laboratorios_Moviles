package com.uvg.laboratorio6

import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.uvg.laboratorio6.ui.theme.Laboratorio6Theme
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.painterResource
import com.uvg.laboratorio6.R
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


//Declaración de variables a usar
val backgroundColor = Color(0xFFF9F8FC)
val blueButtonColor = Color (0xFF445E91)
val incrementBox = Color(0xFF1D7E2A)
val decrementBox = Color(0xFFB3271F)


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Laboratorio6Theme {
                CounterScreen()
            }
        }
    }
}

//Pantalla principal
@Composable
fun CounterScreen(
    modifier: Modifier = Modifier
){
    CounterStructure(modifier = Modifier)

}


//Estructura de la pantalla
@Composable
fun CounterStructure(
    modifier: Modifier = Modifier
){
    // Declaración de variable para estado usando rememberSaveable
    var counterNumber by rememberSaveable { mutableStateOf(0) }
    var counterDecrement by rememberSaveable { mutableStateOf(0) }
    var counterIncrement by rememberSaveable { mutableStateOf(0) }
    var maxValue by rememberSaveable { mutableStateOf(0) }
    var minValue by rememberSaveable { mutableStateOf(0) }
    var totalChanges by rememberSaveable { mutableStateOf(0) }

    // Lista para almacenar el historial de cambios
    val historyList = remember { mutableStateListOf<Pair<Int, Boolean>>() }

    // Box para encerrar todo con un color base
    Box(modifier = Modifier
        .fillMaxSize()
        .background(backgroundColor)
        .padding(10.dp),
        contentAlignment = Alignment.TopCenter) {

        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
            // Texto Principal con Nombre
            Text(text = "Bryan Martínez", style = MaterialTheme.typography.headlineLarge, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(16.dp))
            // Contador
            Counter(
                counterNumber = counterNumber,
                onCounterNumberChange = { newCount ->
                    val isIncrement = newCount > counterNumber
                    counterNumber = newCount

                    // Actualizar maxValue y minValue
                    if (newCount > maxValue) {
                        maxValue = newCount
                    }
                    if (newCount < minValue || totalChanges == 0) {
                        minValue = newCount
                    }

                    // Agregar el nuevo valor al historial
                    historyList.add(Pair(newCount, isIncrement))
                },
                counterDecrement = counterDecrement,
                onCounterDecrementChange = { newCount -> counterDecrement = newCount },
                counterIncrement = counterIncrement,
                onCounterIncrementChange = { newCount -> counterIncrement = newCount },
                totalChanges = totalChanges,
                onTotalChangesChange = { newCount -> totalChanges = newCount }
            )

            // Línea divisora
            FullWidthDivider()
            Spacer(modifier = Modifier.height(16.dp))
            // Textos de información
            displayInfo(info = "Total incrementos", counter = counterIncrement)
            displayInfo(info = "Total decrementos", counter = counterDecrement)
            displayInfo(info = "Valor máximo", counter = maxValue)
            displayInfo(info = "Valor mínimo", counter = minValue)
            displayInfo(info = "Total cambios", counter = totalChanges)
            Spacer(modifier = Modifier.height(16.dp))

            //Texto para historial
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Historial:", fontSize = 29.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Start)
            }

            Spacer(modifier = Modifier.height(16.dp))

            //Grid para el historial
            LazyVerticalGrid(
                columns = GridCells.Fixed(5),
                modifier = Modifier.weight(1f), // Para permitir que el grid ocupe el espacio restante
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(historyList.size) { index ->
                    val item = historyList[index]
                    GridElement(dato = item.first, colorfondo = if (item.second) incrementBox else decrementBox)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón de reinicio
            Button(
                onClick = {
                    // Reiniciar todas las variables
                    counterNumber = 0
                    counterDecrement = 0
                    counterIncrement = 0
                    maxValue = 0
                    minValue = 0
                    totalChanges = 0
                    historyList.clear() // Limpiar el historial
                },
                modifier = Modifier.padding(16.dp).fillMaxWidth()
            ) {
                Text(text = "Reiniciar")
            }
        }
    }
}


//Contador
@Composable
fun Counter(
    modifier: Modifier = Modifier,
    counterNumber: Int,
    onCounterNumberChange: (Int) -> Unit,
    counterDecrement: Int,
    onCounterDecrementChange: (Int) -> Unit,
    counterIncrement: Int,
    onCounterIncrementChange: (Int) -> Unit,
    totalChanges: Int,
    onTotalChangesChange: (Int) -> Unit
){
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(blueButtonColor, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = {
                    onCounterNumberChange(counterNumber - 1)
                    onCounterDecrementChange(counterDecrement + 1)
                    onTotalChangesChange(totalChanges + 1)
                }) {
                    Icon(painter = painterResource(id = R.drawable.icono_menos), contentDescription = "Restar", tint = Color.White, modifier = Modifier.size(15.dp))
                }
            }

            Text(text = counterNumber.toString(), fontSize = 78.sp, textAlign = TextAlign.Center)

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(blueButtonColor, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = {
                    onCounterNumberChange(counterNumber + 1)
                    onCounterIncrementChange(counterIncrement + 1)
                    onTotalChangesChange(totalChanges + 1)
                }) {
                    Icon(Icons.Default.Add, contentDescription = "Sumar", tint = Color.White)
                }
            }
        }
    }
}

//Elementos para grid
@Composable
fun GridElement(modifier: Modifier = Modifier, dato: Int, colorfondo: Color) {
    OutlinedCard(
        modifier = Modifier
            .padding(4.dp)
            .size(50.dp),  // Tamaño ajustado para que sea más compacto
        shape = MaterialTheme.shapes.medium,  // Ajusta la forma si es necesario
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(colorfondo, shape = MaterialTheme.shapes.medium) // Ajusta la forma al color de fondo
        ) {
            Text(text = dato.toString(), color = Color.White, textAlign = TextAlign.Center)
        }
    }
}



//Línea divisora con la información
@Composable
fun FullWidthDivider(modifier: Modifier = Modifier) {
    Divider(
        color = Color.Gray, // Cambia el color de la línea
        thickness = 1.dp,   // Cambia el grosor de la línea
        modifier = Modifier.fillMaxWidth() // Asegura que ocupe todo el ancho
    )
}

//Composable para los textos
@Composable
fun displayInfo(modifier:Modifier = Modifier, info: String, counter: Int){
    Row(modifier = modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
        Text(text = info, textAlign = TextAlign.Start, fontSize = 28.sp,fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
        Text(text = counter.toString(), fontSize = 25.sp,textAlign = TextAlign.Center)
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Laboratorio6Theme {
        CounterScreen()
    }
}