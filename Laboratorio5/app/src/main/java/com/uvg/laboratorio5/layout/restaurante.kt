package com.uvg.laboratorio5.layout

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uvg.laboratorio5.ui.theme.Laboratorio5Theme
import com.uvg.laboratorio5.R
// Colores a utilizar
val fondoPrincipal = Color(0xFFFAF9FD)
val franjaprincipal = Color(0xFFe1f2f8)
val fondoIconoReload = Color(0xFF22a3d3)
val letraTextoActualizacion = Color(0xFF5a5b5c)
val contornoTextoJornada = Color(0xFFB4B4B4)
val colorTextoTerminar = Color(0xFFA470E5)
val fondoBotonIniciar = Color(0xFFFF7654)
val letraDetalles = Color(0xFFFF8E72)
val iconoDireccion = Color (0xFF6408DA)

// Vista principal
@Composable
fun VistaApp(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Box(modifier = Modifier.background(fondoPrincipal)) {
        Column(modifier = Modifier
            .fillMaxWidth()
        ) {
            // Elementos dentro de la columna
            // Box para mensaje de actualización
            Box(modifier = Modifier
                .fillMaxWidth()
                .background(franjaprincipal)
                .height(70.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween // Espacia los elementos
                ) {
                    // Elemento 1 - Icono de reloj
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(fondoIconoReload)
                            .border(2.dp, Color.Transparent, CircleShape)
                            .padding(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(22.dp)
                                .background(Color.Transparent)
                                .clip(CircleShape),
                            tint = Color.White
                        )
                    }
                    // Elemento 2 - Texto de actualización
                    Text(
                        text = "Actualización disponible",
                        fontSize = 13.sp,
                        color = letraTextoActualizacion,
                        modifier = Modifier.padding(start = 15.dp)
                    )

                    // Elemento 3 - Botón Descargar
                    Button(
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW).apply{
                                data = Uri.parse("https://play.google.com/store/apps/details?id=com.whatsapp")
                            }
                            context.startActivity(intent)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = fondoIconoReload
                        ),
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .height(68.dp)
                            .width(160.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Descargar",
                            fontSize = 13.2.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.End,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }


            }
            //Segundo Row - fecha y botón
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(12.dp)) {
                Column(modifier = Modifier
                    .weight(0.5f)
                    .fillMaxHeight()
                    .padding(start = 10.dp), verticalArrangement = Arrangement.Center){
                    Text(text = "Viernes",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.ExtraBold)

                    Text(text = "5 de julio",
                        fontSize = 17.sp,
                        modifier = Modifier.padding(start = 2.dp))

                }

                Box(modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.5f)){
                    Box(
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = contornoTextoJornada,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 8.dp)
                            .height(42.dp)
                            .width(150.dp)
                            .align(Alignment.BottomEnd),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Terminar jornada",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                                color = colorTextoTerminar
                            )
                        }
                    }

            }
            //Tercera parte - Agregar carta con información
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .graphicsLayer {
                        shadowElevation = 8.dp.toPx()
                        shape = RoundedCornerShape(8.dp)
                        clip = false
                    }
                    .height(150.dp),
                color = Color.White
            ){
                //Elementos de la carta
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)){
                    //Row para restaurante y boton de direccion
                    Row(modifier = Modifier
                        .fillMaxWidth().weight(0.2f)
                        , verticalAlignment = Alignment.CenterVertically){
                        Text(text = "Tarantella", fontSize = 22.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))

                        IconButton(
                            onClick = {
                                val gmmIntentUri = Uri.parse("geo:0,0?q=14.495938,-90.481687(Restaurante Tarantella)")
                                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                                mapIntent.setPackage("com.google.android.apps.maps")
                                context.startActivity(mapIntent)
                            }
                        ) {
                            Icon(painter = painterResource(id = R.drawable.direccion), contentDescription = "direccion", tint = iconoDireccion)
                        }
                    }
                    //Información del lugar
                    Text(text = "Deco City, Km. 25.5 Carr. a El Salvador.", fontSize = 13.sp)
                    Text(text = "12:00AM 9:00PM", fontSize = 12.sp, color = contornoTextoJornada)

                    //Espacio vacío
                    Spacer(modifier = Modifier.weight(0.1f))

                    Row(modifier = Modifier.fillMaxWidth().weight(0.3f)){
                        //Boton de Iniciar
                        Button(
                            onClick = {
                                Toast.makeText(context, "Bryan Alberto Martínez Orellana", Toast.LENGTH_SHORT).show()
                            },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = fondoBotonIniciar,
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .height(48.dp)
                                .fillMaxWidth()
                                .weight(0.5f)
                        ) {
                            Text(
                                text = "Iniciar",
                                fontSize = 15.5.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                        //Boton de detalles
                        Button(
                            onClick = {
                                val message = "Tipo de comida: Italiana\nCosto: QQ"
                                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = letraDetalles
                            ),
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .height(48.dp)
                                .fillMaxWidth()
                                .weight(0.5f)
                        ) {
                            Text(
                                text = "Detalles",
                                fontSize = 15.5.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                    }

                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VistaAppPreview() {
    Laboratorio5Theme {
        Surface(modifier = Modifier.fillMaxSize()) {
            VistaApp(
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}