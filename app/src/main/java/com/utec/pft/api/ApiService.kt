package com.utec.pft.api

import com.utec.pft.api.response.AgregarReclamoRequest
import com.utec.pft.api.response.AgregarReclamoResponse
import com.utec.pft.api.response.BorrarReclamoResponse
import com.utec.pft.api.response.LoginRequest
import com.utec.pft.api.response.LoginResponse
import com.utec.pft.api.response.ModificarReclamoRequest
import com.utec.pft.api.response.ModificarReclamoResponse
import com.utec.pft.api.response.ReclamoResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @POST("usuarios/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("reclamos/insert")
    fun agregarReclamo(@Body request: AgregarReclamoRequest): Call<AgregarReclamoResponse>

    @GET("reclamos/listar")
    fun obtenerReclamosAnalista(): Call<List<ReclamoResponse>>

    @GET("reclamos/listar/{idUsuario}")
    fun obtenerReclamos(@Path("idUsuario") idUsuario: Int): Call<List<ReclamoResponse>>

    @PUT("reclamos/delete/{idReclamo}")
    fun eliminarReclamo(@Path("idReclamo") idReclamo: String): Call<BorrarReclamoResponse>

    @PUT("reclamos/update/{idReclamo}")
    fun modificarReclamo(@Path("idReclamo") idReclamo: Int ,@Body request: ModificarReclamoRequest): Call<ModificarReclamoResponse>
}