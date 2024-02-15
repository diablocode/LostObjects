package com.example.lostobjects

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
    val TAG = "Lista de Objetos"
    val database = Firebase.database
    val myRef = database.getReference("objetosPerdidos")
    var objetosPerdidos: ArrayList<ObjetoPerdido?> = ArrayList()
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


        }

        override fun onCancelled(error: DatabaseError) {
            Log.w(TAG, "Failed to read value.", error.toException())
        }

    })

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    LostObjectsTheme {
        ListarObjetos()
    }
}