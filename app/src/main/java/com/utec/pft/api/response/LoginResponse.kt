package com.utec.pft.api.response

import com.utec.pft.objetos.Area
import com.utec.pft.objetos.Departamento
import com.utec.pft.objetos.Itr
import com.utec.pft.objetos.Rol
import com.utec.pft.objetos.Usuario

data class LoginRequest(val nombreUsuario: String, val contrasenia: String)

data class LoginResponse(val anio: Int,
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
                         val validado: Boolean)

// Metodo para pasar la respuesta a un objeto usuario, lo dejo aca para que no sea tanto codigo en el login
fun LoginResponse.toUsuario(): Usuario {
    return Usuario(
        anio = this.anio,
        apellido1 = this.apellido1,
        area = this.area,
        contrasenia = this.contrasenia,
        dep = this.dep,
        documento = this.documento,
        eliminado = this.eliminado,
        email = this.email,
        emailp = this.emailp,
        fechaNacimiento = this.fechaNacimiento,
        fechaRegistro = this.fechaRegistro,
        idUsuario = this.idUsuario,
        itr = this.itr,
        nombre1 = this.nombre1,
        nombreUsuario = this.nombreUsuario,
        rol = this.rol,
        telefono = this.telefono,
        validado = this.validado
    )
}