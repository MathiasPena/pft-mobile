package com.utec.pft.complementos

import java.text.SimpleDateFormat
import java.util.Locale

fun convertirFormatoFecha(fechaOriginal: String): String {

    try {
        // Crear un objeto SimpleDateFormat para el formato original
        val formatoOriginal = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        // Parsear la fecha original
        val fecha = formatoOriginal.parse(fechaOriginal)

        // Crear un objeto SimpleDateFormat para el nuevo formato
        val formatoNuevo = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        // Formatear la fecha en el nuevo formato
        return formatoNuevo.format(fecha)
    } catch (e: Exception) {
        // Manejar la excepción en caso de que la fecha no sea válida
        return "Formato de fecha incorrecto"
    }
}