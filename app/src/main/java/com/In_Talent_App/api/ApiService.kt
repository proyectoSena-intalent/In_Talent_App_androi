package com.In_Talent_App.api

import com.In_Talent_App.model.AuthResponse // O el modelo de respuesta que devuelva tu servidor
import com.In_Talent_App.model.LoginRequest
import com.In_Talent_App.model.RegisterRequest // O como se llame tu data class de registro
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import com.In_Talent_App.model.ServiceResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {

    @POST("api/auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<AuthResponse>

    // AGREGAR ESTA FUNCIÓN:
    @POST("api/auth/register") // Ajusta la ruta exacta según tu backend Node.js
    suspend fun registrar(
        @Body request: RegisterRequest
    ): Response<AuthResponse> // O el modelo que retorne tu endpoint
    @GET("api/servicios")
    suspend fun getServices(
        @Header("Authorization") token: String
    ): Response<List<ServiceResponse>>
}