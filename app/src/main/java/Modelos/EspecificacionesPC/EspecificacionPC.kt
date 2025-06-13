package Modelos.EspecificacionesPC

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class EspecificacionPC(
    @SerializedName("dispositivoId")
    val dispositivoId: Int,

    @SerializedName("cpu")
    val cpu: String?,

    @SerializedName("ram")
    val ram: String?,

    @SerializedName("hd")
    val hd: String?,

    @SerializedName("sistemaOperativo")
    val sistemaOperativo: String?
) : Serializable