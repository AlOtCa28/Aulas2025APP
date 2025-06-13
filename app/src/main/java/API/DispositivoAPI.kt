package API

import Modelos.Dispositivo.Dispositivo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface DispositivoAPI {

    @GET("dispositivos/listado")
    suspend fun listarDispositivos(): Response<MutableList<Dispositivo>>

    @GET("dispositivos/{id}")
    suspend fun obtenerDispositivoPorId(@Path("id") id: Long): Response<Dispositivo>

    @POST("dispositivos/registrar")
    suspend fun registrarDispositivo(@Body dispositivo: Dispositivo): Response<Boolean>

    @PUT("dispositivos/actualizar")
    suspend fun actualizarDispositivo(@Body dispositivo: Dispositivo): Response<Boolean>

    @DELETE("dispositivos/eliminar/{id}")
    suspend fun eliminarDispositivo(@Path("id") id: Int): Response<Boolean>
}