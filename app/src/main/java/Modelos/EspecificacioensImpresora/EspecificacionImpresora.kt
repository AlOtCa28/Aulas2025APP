package Modelos.EspecificacioensImpresora

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class EspecificacionImpresora(
    @SerializedName("dispositivoId")
    val dispositivoId: Int,

    @SerializedName("tipo")
    val tipo: String?
) : Serializable
