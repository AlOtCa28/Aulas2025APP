package API

import Modelos.EspecificacionesPC.EspecificacionPC
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface EspecificacionPcAPI {

    @GET("especificacion-pc/listado")
    suspend fun listarEspecificaciones(): Response<MutableList<EspecificacionPC>>

    @GET("especificacion-pc/{id}")
    suspend fun obtenerEspecificacionPorId(@Path("id") id: Int): Response<EspecificacionPC>

    @POST("especificacion-pc/registrar")
    suspend fun registrarEspecificacion(@Body especificacion: EspecificacionPC): Response<Boolean>

    @PUT("especificacion-pc/actualizar")
    suspend fun actualizarEspecificacion(@Body especificacion: EspecificacionPC): Response<Boolean>

    @DELETE("especificacion-pc/eliminar/{dispositivoId}")
    suspend fun eliminarEspecificacion(@Path("dispositivoId") id: Int): Response<Boolean>
}