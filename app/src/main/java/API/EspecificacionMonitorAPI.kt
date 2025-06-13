package API

import Modelos.EspecificacionesMonitor.EspecificacionMonitor
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface EspecificacionMonitorAPI {

    @GET("especificacion-monitor/listado")
    suspend fun listarEspecificaciones(): Response<MutableList<EspecificacionMonitor>>

    @GET("especificacion-monitor/{id}")
    suspend fun obtenerEspecificacionPorId(@Path("id") id: Int): Response<EspecificacionMonitor>

    @POST("especificacion-monitor/registrar")
    suspend fun registrarEspecificacion(@Body especificacion: EspecificacionMonitor): Response<Boolean>

    @PUT("especificacion-monitor/actualizar")
    suspend fun actualizarEspecificacion(@Body especificacion: EspecificacionMonitor): Response<Boolean>

    @DELETE("especificacion-monitor/eliminar/{dispositivoId}")
    suspend fun eliminarEspecificacion(@Path("dispositivoId") id: Int): Response<Boolean>
}