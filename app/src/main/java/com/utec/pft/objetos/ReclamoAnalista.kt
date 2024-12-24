package com.utec.pft.objetos

import android.os.Parcel
import android.os.Parcelable

data class ReclamoAnalista(
    // documento usuario o otro identificador ...
    var id: String,
    var tipo: String,
    var titulo: String,
    var desc: String,
    var fechaYHora: String,
    var semestre: String,
    var fechaRegistro: String,
    var docente: String,
    var creditos: String,
    var nombrePersona: String,
    var rolPersona: String,
    var estado: String,

    ) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(tipo)
        parcel.writeString(titulo)
        parcel.writeString(desc)
        parcel.writeString(fechaYHora)
        parcel.writeString(semestre)
        parcel.writeString(fechaRegistro)
        parcel.writeString(docente)
        parcel.writeString(creditos)
        parcel.writeString(nombrePersona)
        parcel.writeString(rolPersona)
        parcel.writeString(estado)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ReclamoAnalista> {
        override fun createFromParcel(parcel: Parcel): ReclamoAnalista {
            return ReclamoAnalista(parcel)
        }

        override fun newArray(size: Int): Array<ReclamoAnalista?> {
            return arrayOfNulls(size)
        }
    }
}