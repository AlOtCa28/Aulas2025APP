package API

import Modelo.Usuario.Usuario
import Modelo.Usuario.UsuarioLogIn
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UsuarioAPI {
    @GET("usuarios/{nombre}")
    suspend fun obtenerUsuarioPorNombre(@Path("nombre") nombre: String): Response<Usuario>

    @POST("usuarios/login")
    suspend fun iniciarSesion(@Body usuerData: UsuarioLogIn): Response<Usuario?>

    @GET("usuarios/listado")
    suspend fun listarUsuarios(): Response<MutableList<Usuario>>

    @GET("usuarios/actualizar/{id}")
    suspend fun actualizarUsuario(@Path("id") id: Int): Response<Boolean>

    @POST("usuarios/registrar")
    suspend fun registrarUsuario(@Body usuario: Usuario): Response<Boolean>

    @DELETE("usuarios/borrar/{id}")
    suspend fun borrarUsuario(@Path("id") id: Int): Response<Boolean>
}
