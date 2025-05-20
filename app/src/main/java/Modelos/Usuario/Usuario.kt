package Modelo.Usuario

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Usuario(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("nombre")
    val nombre: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("passwordHash")
    val passwordHash: String,

    @SerializedName("rol")
    val rol: Int
) : Serializable
