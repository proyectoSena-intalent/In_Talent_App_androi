package com.In_Talent_App.model

import com.google.gson.annotations.SerializedName

data class ServiceResponse(
    // Columna ID primaria en MySQL (id, id_servicio, etc.)
    @SerializedName("id") val id: Int?,

    // Revisa cómo se llama la columna en tu tabla MySQL ('titulo', 'nombre', 'title')
    @SerializedName("titulo", alternate = ["title", "nombre_servicio"])
    val title: String?,

    // Revisa el nombre de la columna para la descripción
    @SerializedName("descripcion", alternate = ["description"])
    val description: String?,

    // Revisa el nombre de la columna para la categoría
    @SerializedName("categoria", alternate = ["category"])
    val category: String?,

    // Revisa el nombre de la columna para el precio
    @SerializedName("precio", alternate = ["price"])
    val price: Double?,

    // Opcionales por si usas calificación o imágenes
    @SerializedName("calificacion", alternate = ["rating"])
    val rating: Double?,

    @SerializedName("imagen_url", alternate = ["imageUrl", "imagen"])
    val imageUrl: String?
)