package com.utec.pft.objetos

import java.time.LocalDateTime

data class Evento(
    var idEvento: Int? = null,
    var titulo: String? = null,
    var ubicacion: String? = null,
    var eliminado: Boolean = false,
    var inicio: LocalDateTime? = null,
    var fin: LocalDateTime? = null,
    var objItr: Itr? = null,
  //  var objM: Modalidad? = null,
   // var objTipoEvento: TipoEvento? = null,
    //var objEstado: Estado? = null,
    var encargados: List<Usuario>? = null
)