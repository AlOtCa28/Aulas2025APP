package com.example.aulas2025app.JEFE.JefeAulas.DetalleAula

import API.UserNetwork
import Modelos.Aulas.Aula
import Modelos.Dispositivo.Dispositivo
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class DetalleAulaViewModel : ViewModel() {

    private val _resultadoGuardado = MutableLiveData<Boolean>()
    val resultadoGuardado: LiveData<Boolean> get() = _resultadoGuardado

    private val _dispositivos = MutableLiveData<List<Dispositivo>>()
    val dispositivos: LiveData<List<Dispositivo>> get() = _dispositivos

    private val _mensajeError = MutableLiveData<String?>()
    val mensajeError: LiveData<String?> get() = _mensajeError

    private val dispositivoRepository = UserNetwork.retrofitDispositivos
    private val aulaRepository = UserNetwork.retrofitAulas

    fun obtenerDispositivosPorAula(idAula: Int) {
        viewModelScope.launch {
            try {
                val response = dispositivoRepository.obtenerDispositivosPorAula(idAula)
                if (response.isSuccessful) {
                    Log.d("DEBUG_AULA", "Respuesta retrofit: ${response.body()?.size ?: 0}")
                    _dispositivos.postValue(response.body() ?: emptyList())
                } else {
                    _dispositivos.postValue(emptyList())
                    _mensajeError.postValue("Error: ${response.code()}")
                }
            } catch (e: Exception) {
                _dispositivos.postValue(emptyList())
                _mensajeError.postValue("Error: ${e.message}")
            }
        }
    }




    fun guardarAula(aula: Aula, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val response = if (aula.idAula == null) {
                    aulaRepository.registrarAula(aula)
                } else {
                    aulaRepository.actualizarAula(aula)
                }
                callback(response.isSuccessful && response.body() == true)
            } catch (e: Exception) {
                callback(false)
            }
        }
    }
}
