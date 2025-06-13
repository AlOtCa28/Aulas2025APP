package API

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import Parametros.Parametros

object UserNetwork {
    val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Parametros.url+":"+Parametros.puerto)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UsuarioAPI::class.java)
    }
    val retrofitAulas by lazy {
        Retrofit.Builder()
            .baseUrl(Parametros.url+":"+Parametros.puerto)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AulasAPI::class.java)
    }
    val retrofitEspecificacionesPC by lazy {
        Retrofit.Builder()
            .baseUrl(Parametros.url+":"+Parametros.puerto)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EspecificacionPcAPI::class.java)
    }
    val retrofitEspecificacionImpresora by lazy {
        Retrofit.Builder()
            .baseUrl(Parametros.url+":"+Parametros.puerto)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EspecificacionImpresoraAPI::class.java)
    }
    val retrofitDispositivos by lazy {
        Retrofit.Builder()
            .baseUrl(Parametros.url+":"+Parametros.puerto)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DispositivoAPI::class.java)
    }
    val retrofitEspecificacionMonitor by lazy {
        Retrofit.Builder()
            .baseUrl(Parametros.url+":"+Parametros.puerto)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EspecificacionMonitorAPI::class.java)
    }

    val retrofitMensajes by lazy {
        Retrofit.Builder()
            .baseUrl(Parametros.url + ":" + Parametros.puerto)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MensajeAPI::class.java)
    }

    val retrofitMensajeUsuario by lazy {
        Retrofit.Builder()
            .baseUrl(Parametros.url + ":" + Parametros.puerto)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MensajeUsuarioAPI::class.java)
    }
}