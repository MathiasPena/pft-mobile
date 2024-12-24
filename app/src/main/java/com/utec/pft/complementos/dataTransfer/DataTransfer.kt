package com.utec.pft.complementos.dataTransfer

import com.utec.pft.api.response.LoginResponse
import com.utec.pft.objetos.Usuario

// Singletons para transportar datos
class DataTransfer private constructor() {

    //idusuario solamente cuando se necesita
    private var idusuario: Int = 0
    //Usuario completo, solamente para asignarlo en el fragmento de Perfil
    private var usuario: LoginResponse? = null
    //idReclamo cuando lo necesite
    private var idReclamo: Int = 0

    private var estadoModificado: Int = 0

    companion object {
        private val instance = DataTransfer()

        fun getInstance(): DataTransfer {
            return instance
        }
    }

    fun guardarEstado(estado: Int) {
        estadoModificado = estado
    }

    fun obtenerEstado(): Int {
        return estadoModificado
    }

    fun guardarUsuario(usuarioNuevo: LoginResponse) {
        usuario = usuarioNuevo
    }

    fun obtenerUsuario(): LoginResponse? {
        return usuario
    }

    fun guardarIdusuario(idUsuarioNuevo: Int) {
        idusuario = idUsuarioNuevo
    }

    fun obtenerIdusuario(): Int {
        return idusuario
    }

    fun guardarIdReclamo(idReclamoNuevo: Int) {
        idReclamo = idReclamoNuevo
    }

    fun obtenerIdReclamo(): Int {
        return idReclamo
    }


}