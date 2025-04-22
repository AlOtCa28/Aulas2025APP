package Modelos.Aulas

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Aula(
    @SerializedName("id")
    val idAula: Int? = null,

    @SerializedName("nombre")
    val nombreAula: String,

    @SerializedName("idEncargado")
    val idEncargado: Int
) : Serializable
