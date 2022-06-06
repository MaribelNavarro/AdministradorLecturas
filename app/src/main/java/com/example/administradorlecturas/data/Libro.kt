package com.example.administradorlecturas.data

import java.io.Serializable
import java.util.*

//Clase para definir los atributos del libro
data class Libro(
    val idLibro : Int = getAutoId(),
    val tituloLibro: String = "",
    val autorLibro: String = "",
    val tipoLibro: String = "",
    val urlImagenLibro: String = "",
    val estadoLibro: String = "",
    val totalPagsLibro: String = "",
    val pagsLeidasLibro: String = "",
): Serializable {
    companion object {
        //Función para generar el id automaticamente
        fun getAutoId(): Int{
            val random = Random()
            return random.nextInt(100)
        }//fin del la función getAutoId
    }
}
