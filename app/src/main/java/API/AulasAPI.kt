package API

import Modelos.Aulas.Aula
import retrofit2.Response
import retrofit2.http.GET

interface AulasAPI {

    @GET("aulas/listado")
    suspend fun listarAulas(): Response<MutableList<Aula>>
}