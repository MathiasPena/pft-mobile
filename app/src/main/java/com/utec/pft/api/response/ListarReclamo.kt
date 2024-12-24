package com.utec.pft.api.response

import com.utec.pft.objetos.Reclamo
import com.utec.pft.objetos.ReclamoAnalista

data class ReclamoResponse(
    val creditos: Int,
    val descripcion: String,
    val docente: String,
    val eliminado: Boolean,
    val estado: String,
    val estudiante: Estudiante,
    val fechaEvento: String,
    val fechaRegistro: String,
    val idReclamo: Int,
    val respuesta: String,
    val semestre: Int,
    val tipoReclamo: String,
    val tituloReclamo: String
)

data class Estudiante(
    val anio: Int,
    val apellido1: String,
    val area: Area,
    val contrasenia: String,
    val dep: Departamento,
    val documento: String,
    val eliminado: Boolean,
    val email: String,
    val emailp: String,
    val fechaNacimiento: String,
    val fechaRegistro: String,
    val idUsuario: Int,
    val itr: Itr,
    val nombre1: String,
    val nombreUsuario: String,
    val rol: Rol,
    val telefono: String,
    val validado: Boolean
)

data class Area(
    val idArea: Int,
    val nombre: String
)

data class Departamento(
    val idDep: Int,
    val nombre: String
)

data class Itr(
    val descripcion: String,
    val eliminado: Boolean,
    val idItr: Int,
    val nombre: String
)

data class Rol(
    val estado: Boolean,
    val idRol: Int,
    val nombre: String
)

fun convertirReclamoResponseAReclamo(reclamoResponse: ReclamoResponse): Reclamo {
    return Reclamo(
        id = reclamoResponse.idReclamo.toString(),
        tipo = reclamoResponse.tipoReclamo,
        titulo = reclamoResponse.tituloReclamo,
        desc = reclamoResponse.descripcion,
        fechaYHora = reclamoResponse.fechaEvento,
        semestre = reclamoResponse.semestre.toString(),
        respuesta = reclamoResponse.respuesta,
        fechaRegistro = reclamoResponse.fechaRegistro,
        docente = reclamoResponse.docente,
        creditos = reclamoResponse.creditos.toString(),
        estado = reclamoResponse.estado
    )
}

fun convertirReclamoResponseAReclamoAnalista(reclamoResponse: ReclamoResponse): ReclamoAnalista {
    return ReclamoAnalista(
        id = reclamoResponse.idReclamo.toString(),
        tipo = reclamoResponse.tipoReclamo,
        titulo = reclamoResponse.tituloReclamo,
        desc = reclamoResponse.descripcion,
        fechaYHora = reclamoResponse.fechaEvento,
        semestre = reclamoResponse.semestre.toString(),
        fechaRegistro = reclamoResponse.fechaRegistro,
        docente = reclamoResponse.docente,
        creditos = reclamoResponse.creditos.toString(),
        nombrePersona = reclamoResponse.estudiante.nombre1 + " " + reclamoResponse.estudiante.apellido1,
        rolPersona = reclamoResponse.estudiante.rol.nombre,
        estado = reclamoResponse.estado
    )
}