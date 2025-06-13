package Modelos.Mensaje

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MensajeUsuario(
    @SerializedName("mensajeId") val mensajeId: Long,
    @SerializedName("usuarioCorreo") val usuarioCorreo: String,
    @SerializedName("mostrado") val mostrado: Boolean
) : Serializable
