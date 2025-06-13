package API

import Modelos.EspecificacioensImpresora.EspecificacionImpresora
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface EspecificacionImpresoraAPI {

    @GET("especificacion-impresora/listado")
    suspend fun listarEspecificaciones(): Response<MutableList<EspecificacionImpresora>>

    @GET("especificacion-impresora/{id}")
    suspend fun obtenerEspecificacionPorId(@Path("id") id: Int): Response<EspecificacionImpresora>

    @POST("especificacion-impresora/registrar")
    suspend fun registrarEspecificacion(@Body especificacion: EspecificacionImpresora): Response<Boolean>

    @PUT("especificacion-impresora/actualizar")
    suspend fun actualizarEspecificacion(@Body especificacion: EspecificacionImpresora): Response<Boolean>

    @DELETE("especificacion-impresora/eliminar/{dispositivoId}")
    suspend fun eliminarPorDispositivo(@Path("dispositivoId") dispositivoId: Int): Response<Boolean>
}