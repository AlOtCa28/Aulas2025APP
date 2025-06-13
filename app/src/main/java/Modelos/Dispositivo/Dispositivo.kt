package Modelos.Dispositivo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Dispositivo(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("codigo")
    val codigo: String,

    @SerializedName("descripcion")
    val descripcion: String,

    @SerializedName("estado")
    val estado: String,

    @SerializedName("marca")
    val marca: String,

    @SerializedName("modelo")
    val modelo: String,

    @SerializedName("numeroSerie")
    val numeroSerie: String,

    @SerializedName("ubicacionReferencia")
    val ubicacionReferencia: String,

    @SerializedName("ubicacionActual")
    val ubicacionActual: String,

    @SerializedName("aulaId")
    val aulaId: Int?,

    @SerializedName("tipo")
    val tipo: String
) : Serializable

