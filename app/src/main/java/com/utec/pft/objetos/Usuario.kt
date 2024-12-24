package com.utec.pft.objetos

data class Usuario(
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

data class Rol(
    val idRol: Int,
    val nombre: String
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
    val idItr: Int,
    val nombre: String
)