package com.uvg.laboratorio5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.uvg.laboratorio5.layout.VistaApp
import com.uvg.laboratorio5.ui.theme.Laboratorio5Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Laboratorio5Theme {
                VistaApp()  // Llama a la función VistaApp para mostrar la UI
            }
        }
    }
}
