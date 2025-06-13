package com.example.aulas2025app.JEFE.JefeDispositivos.DetalleDispositivo

import API.UserNetwork
import Modelos.Dispositivo.Dispositivo
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class DetalleDispositivoViewModel : ViewModel() {

    private val _dispositivo = MutableLiveData<Dispositivo>()
    val dispositivo: LiveData<Dispositivo> = _dispositivo

    private val _mensajeError = MutableLiveData<String?>()
    val mensajeError: LiveData<String?> = _mensajeError

    fun cargarDispositivo(id: Long) {
        viewModelScope.launch {
            try {
                val response = UserNetwork.retrofitDispositivos.obtenerDispositivoPorId(id)
                if (response.isSuccessful) {
                    _dispositivo.postValue(response.body())
                } else {
                    _mensajeError.postValue("Error cargando dispositivo: ${response.message()}")
                }
            } catch (e: Exception) {
                _mensajeError.postValue("Error: ${e.message}")
            }
        }
    }

    fun guardarDispositivo(dispositivo: Dispositivo, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val response = UserNetwork.retrofitDispositivos.actualizarDispositivo(dispositivo)
                if (response.isSuccessful && response.body() == true) {
                    onComplete(true)
                } else {
                    onComplete(false)
                }
            } catch (e: Exception) {
                onComplete(false)
            }
        }
    }
}
