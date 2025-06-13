package API

import Modelos.Mensaje.Mensaje
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MensajeAPI {
    @GET("mensajes/listado")
    suspend fun listarMensajes(): List<Mensaje>

    @POST("mensajes/registrar")
    suspend fun registrarMensaje(@Body mensaje: Mensaje): Boolean

    @PUT("mensajes/actualizar")
    suspend fun actualizarMensaje(@Body mensaje: Mensaje): Boolean

    @DELETE("mensajes/borrar/{id}")
    suspend fun eliminarMensaje(@Path("id") id: Long): Boolean
}