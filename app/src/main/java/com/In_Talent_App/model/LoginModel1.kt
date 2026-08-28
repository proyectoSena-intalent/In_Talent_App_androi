package com.In_Talent_App.model

// Datos para Login
data class LoginRequest(
    val email: String,
    val contrasena: String
)

// Respuesta general del backend
data class AuthResponse(
    val mensaje: String?,
    val token: String?,
    val error: String?
)

// DATOS PARA REGISTRO
data class RegisterRequest(
    val nombre: String,
    val email: String,
    val contrasena: String,
    val telefono: String? = null,
    val rol: String = "CLIENTE"
)