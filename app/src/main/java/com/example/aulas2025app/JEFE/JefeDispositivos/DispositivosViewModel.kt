package com.example.aulas2025app.JEFE.JefeDispositivos

import API.UserNetwork
import Modelos.Dispositivo.Dispositivo
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class DispositivosViewModel : ViewModel() {

    private val _myResponseList = MutableLiveData<List<Dispositivo>>()
    val myResponseList: LiveData<List<Dispositivo>> get() = _myResponseList

    private val _resultado = MutableLiveData<Boolean>()
    val resultado: LiveData<Boolean> get() = _resultado

    private val _nuevoDispositivoId = MutableLiveData<Int?>()
    val nuevoDispositivoId: LiveData<Int?> get() = _nuevoDispositivoId

    val error = MutableLiveData<String?>()

    // Retrofit clients para especificaciones (ya definidos en UserNetwork)
    private val retrofitPC = UserNetwork.retrofitEspecificacionesPC
    private val retrofitMonitor = UserNetwork.retrofitEspecificacionMonitor
    private val retrofitImpresora = UserNetwork.retrofitEspecificacionImpresora
    private val retrofitDispositivos = UserNetwork.retrofitDispositivos

    fun getDispositivosVM() {
        viewModelScope.launch {
            try {
                val response = retrofitDispositivos.listarDispositivos()
                if (response.isSuccessful && response.body() != null) {
                    _myResponseList.postValue(response.body())
                } else {
                    error.postValue("Error al cargar dispositivos: ${response.code()}")
                }
            } catch (e: Exception) {
                error.postValue("Excepción: ${e.message}")
            }
        }
    }

    fun crearDispositivo(dispositivo: Dispositivo) {
        viewModelScope.launch {
            try {
                val response = retrofitDispositivos.registrarDispositivo(dispositivo)
                if (response.isSuccessful && response.body() == true) {
                    val listaResponse = retrofitDispositivos.listarDispositivos()
                    if (listaResponse.isSuccessful) {
                        val lista = listaResponse.body()
                        val ultimo = lista?.maxByOrNull { it.id ?: 0 }
                        _nuevoDispositivoId.postValue(ultimo?.id)
                        _resultado.postValue(true)
                    } else {
                        _resultado.postValue(false)
                        error.postValue("No se pudo recuperar el ID tras crear")
                    }
                } else {
                    _resultado.postValue(false)
                    error.postValue("Error al registrar dispositivo")
                }
            } catch (e: Exception) {
                _resultado.postValue(false)
                error.postValue("Excepción: ${e.message}")
            }
        }
    }

    fun eliminarDispositivoYDependencias(id: Int, onResult: (success: Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                Log.d("DispositivosViewModel", "Intentando eliminar especificaciones para dispositivo id: $id")

                val eliminarPc = retrofitPC.eliminarEspecificacion(id)
                val eliminarMonitor = retrofitMonitor.eliminarEspecificacion(id)
                val eliminarImpresora = retrofitImpresora.eliminarPorDispositivo(id)

                // Logs para ver detalles de la respuesta
                Log.d("DispositivosViewModel", "Eliminar PC - código HTTP: ${eliminarPc.code()}, mensaje: ${eliminarPc.message()}, errorBody: ${eliminarPc.errorBody()?.string()}")
                Log.d("DispositivosViewModel", "Eliminar Monitor - código HTTP: ${eliminarMonitor.code()}, mensaje: ${eliminarMonitor.message()}, errorBody: ${eliminarMonitor.errorBody()?.string()}")
                Log.d("DispositivosViewModel", "Eliminar Impresora - código HTTP: ${eliminarImpresora.code()}, mensaje: ${eliminarImpresora.message()}, errorBody: ${eliminarImpresora.errorBody()?.string()}")

                if (eliminarPc.isSuccessful && eliminarMonitor.isSuccessful && eliminarImpresora.isSuccessful) {
                    val eliminarDispResp = retrofitDispositivos.eliminarDispositivo(id)
                    if (eliminarDispResp.isSuccessful && eliminarDispResp.body() == true) {
                        getDispositivosVM()
                        onResult(true)
                    } else {
                        onResult(false)
                    }
                } else {
                    Log.e("DispositivosViewModel", "Error al eliminar alguna especificacion (PC/Monitor/Impresora)")
                    onResult(false)
                }
            } catch (e: Exception) {
                Log.e("DispositivosViewModel", "Exception al eliminar dispositivo y dependencias", e)
                onResult(false)
            }
        }
    }





}


