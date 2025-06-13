package Modelos.EspecificacionesMonitor

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class EspecificacionMonitor(
    @SerializedName("dispositivoId")
    val dispositivoId: Int,

    @SerializedName("resolucionMaxima")
    val resolucionMaxima: String?,

    @SerializedName("pulgadas")
    val pulgadas: Double?,

    @SerializedName("orientable")
    val orientable: Boolean?
) : Serializable
