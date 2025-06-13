package API

import Modelos.Mensaje.Mensaje
import Modelos.Mensaje.MensajeUsuario
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MensajeUsuarioAPI {
    @POST("mensajeusuario/registrar")
    suspend fun registrarMensajeUsuario(@Body mensajeUsuario: MensajeUsuario): Boolean

    @PUT("mensajeusuario/marcar-mostrado")
    suspend fun marcarMensajeMostrado(@Body mensajeUsuario: MensajeUsuario): Boolean

    @GET("mensajeusuario/no-mostrados/{usuarioCorreo}")
    suspend fun obtenerNoMostrados(@Path("usuarioCorreo") correo: String): List<Mensaje>

    @DELETE("mensajeusuario/borrar-por-mensaje/{mensajeId}")
    suspend fun borrarPorMensaje(@Path("mensajeId") id: Long): Boolean
}