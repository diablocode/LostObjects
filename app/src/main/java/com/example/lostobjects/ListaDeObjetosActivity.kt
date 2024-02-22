package com.example.lostobjects

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lostobjects.ui.theme.LostObjectsTheme
import com.example.objetosperdidos.ObjetoPerdido
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database


class ListaDeObjetosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LostObjectsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ListarObjetos()
                }
            }
        }
    }
}

@Composable
fun ListarObjetos() {
    var listaDeObjetos: List<ObjetoPerdido?> by remember { mutableStateOf(emptyList()) }
    var objetosPerdidos = mutableListOf<ObjetoPerdido?>()

    val TAG = "Lista de Objetos"
    val database = Firebase.database
    val myRef = database.getReference("objetosPerdidos")
    myRef.addValueEventListener(object: ValueEventListener {

        override fun onDataChange(snapshot: DataSnapshot) {
            // This method is called once with the initial value and again
            // whenever data at this location is updated.
            val value = snapshot.children
            for (data in value){
                var objetoPerdido = data.getValue(ObjetoPerdido::class.java)

                Log.d(TAG, objetoPerdido?.descripcion + " " +
                        objetoPerdido?.ubicacion)
                objetosPerdidos.add(objetoPerdido)
            }
            listaDeObjetos = objetosPerdidos

        }

        override fun onCancelled(error: DatabaseError) {
            Log.w(TAG, "Failed to read value.", error.toException())
        }

    })
    imprimirObjetos(listaDeObjetos)

}
@Composable
fun imprimirObjetos(lista: List<ObjetoPerdido?>){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                listOf(
                    Color(0xFFacfaf5),
                    Color(0xFFe6f7f6)
                ))
            )
            .padding(40.dp),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        items(lista.size){
            indice ->
                var op = lista[indice]
                Row(){
                    Text(""+op?.descripcion,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        color = Color.Blue,
                        modifier = Modifier.padding(10.dp)

                    )
                    Text(""+op?.ubicacion,
                        modifier = Modifier.padding(10.dp))
                }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    LostObjectsTheme {

        imprimirObjetos(emptyList())
    }
}