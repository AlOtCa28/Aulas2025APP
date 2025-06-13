package Modelos.Mensaje

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Mensaje(
    @SerializedName("id") val id: Long? = null,
    @SerializedName("titulo") val titulo: String,
    @SerializedName("contenido") val contenido: String,
    @SerializedName("fecha_envio") val fechaEnvio: String, // ISO 8601 preferido
    @SerializedName("url_imagen") val urlImagen: String? = null
) : Serializable