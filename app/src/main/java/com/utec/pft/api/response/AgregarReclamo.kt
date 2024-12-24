package com.utec.pft.api.response

data class AgregarReclamoRequest(
    val tipo: String,
    val tituloReclamo: String,
    val descripcion: String,
    val fechaRegistro: String,
    val docente: String,
    val semestre: String,
    val fechaYHora: String,
    val creditos: String,
    val id: Int,
    val estado: String = "Abierto",
    val eliminado: Boolean = false
)

data class AgregarReclamoResponse(var respuesta: String)