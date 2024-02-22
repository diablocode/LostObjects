package com.example.lostobjects

import android.app.ListActivity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lostobjects.ui.theme.LostObjectsTheme
import com.example.objetosperdidos.ObjetoPerdido
import com.google.firebase.Firebase
import com.google.firebase.database.database

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LostObjectsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PantallaAgregarObjeto()
                }
            }
        }
    }


    @Composable
    fun PantallaAgregarObjeto() {
        var descripcionObjetoPerdido by remember { mutableStateOf("") }
        var ubicacion by remember { mutableStateOf("") }

        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.linearGradient(
                    listOf(
                        Color(0xFFacfaf5),
                        Color(0xFFe6f7f6)))
                )
                .padding(40.dp),

            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text("Objeto")
            BasicTextField(
                value = descripcionObjetoPerdido,
                onValueChange = {descripcionObjetoPerdido = it},
                modifier = Modifier
                    .padding(5.dp)
                    .border(2.dp, Color.Black)
                    .background(Color.White)
                    .height(30.dp)
                    .fillMaxWidth()

            )

            Text("", modifier = Modifier.padding(10.dp))
            Text("Ubicación")
            BasicTextField(
                value = ubicacion,
                onValueChange = {ubicacion = it},
                modifier = Modifier
                    .padding(5.dp)
                    .border(2.dp, Color.Black)
                    .background(Color.White)
                    .height(30.dp)
                    .fillMaxWidth()

            )
            Button(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color.Black),
                onClick = {
                var objetoPerdido = ObjetoPerdido()
                objetoPerdido.descripcion = descripcionObjetoPerdido
                objetoPerdido.ubicacion = ubicacion

                val database = Firebase.database
                val myRef = database.getReference("objetosPerdidos")

                myRef.child(objetoPerdido.hashCode().toString()).
                    setValue(objetoPerdido)
                descripcionObjetoPerdido = ""
                ubicacion = ""
            }) {
                Text(text = "Agregar")
            }
            Button(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color.Black),
                onClick = {
                    var intent = Intent(this@MainActivity,
                        ListaDeObjetosActivity::class.java)
                    startActivity(intent)
                 }) {
                Text(text = "Ver Lista")
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        LostObjectsTheme {
            PantallaAgregarObjeto()
        }
    }
}

