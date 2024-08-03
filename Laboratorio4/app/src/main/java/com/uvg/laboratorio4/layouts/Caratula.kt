package com.uvg.laboratorio4.layouts

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uvg.laboratorio4.R
import com.uvg.laboratorio4.ui.theme.Laboratorio4Theme


val BorderColor = Color(0xFF066805)

@Composable
//Función principal
fun caratulaUVG(modifier: Modifier = Modifier){
    //Se arreglarán como una columna todos los datos
    Box(modifier = Modifier.border(7.5.dp, BorderColor)) {
        //Imagen de fondo
        Image(painter = painterResource(id = R.drawable.logo), contentDescription = "logo de UVG", modifier.fillMaxSize().graphicsLayer(alpha = 0.12f).fillMaxSize(), contentScale = ContentScale.Crop)
        Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {

            //Primer elemento para nombre de U
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 210.dp)
            ) {
                Text(
                    text = "Universidad del Valle de Guatemala",
                    color = Color.Black,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    lineHeight = 48.sp,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }
            //Segundo elemento - nombre del curso
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp, bottom = 15.dp)
            ) {
                Text(
                    text = "Programación de plataformas móviles, Sección 30",
                    color = Color.Black,
                    fontSize = 23.7.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    lineHeight = 40.sp,
                    modifier = Modifier.padding(start = 6.dp, end = 6.dp)
                )
            }

            //Tercer elemento
            filaNegrita(modifier = Modifier, "INTEGRANTES", "Bryan Martínez\nAdriana Palacios")

            //Tercer elemento
            filaNegrita(
                modifier = Modifier.padding(top = 20.dp),
                "CATEDRÁTICO",
                "Juan Carlos Durini"
            )

            //Quinto elemento (información propia)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {
                Text(
                    text = "Bryan Martínez\n23542",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }

}

@Composable
fun filaNegrita(modifier: Modifier = Modifier.padding(horizontal = 30.dp), negrita: String, normal: String) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        Text(
            text = negrita,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 20.dp)
        )
        Text(
            text = normal,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp,
            modifier = Modifier
                .weight(1f)
                .padding(end = 20.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewCaratula(){
    Laboratorio4Theme{
        Surface(modifier = Modifier.fillMaxSize()){
            caratulaUVG(modifier = Modifier.fillMaxSize())
        }
    }
}