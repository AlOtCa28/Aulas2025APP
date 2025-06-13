package API

import Modelos.Aulas.Aula
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AulasAPI {

    @GET("aulas/listado")
    suspend fun listarAulas(): Response<MutableList<Aula>>


    @POST("aulas/listado/registrar")
    suspend fun registrarAula(@Body aula: Aula): Response<Boolean>

    @DELETE("aulas/listado/eliminar/{id}")
    suspend fun eliminarAula(@Path("id") id: Int): Response<Boolean>
}