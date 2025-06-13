package com.example.aulas2025app.JEFE.JefeDispositivos.Especificaciones.EspecificacionMonitor

import API.UserNetwork
import Modelos.EspecificacionesMonitor.EspecificacionMonitor
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class EspecificacionMonitorViewModel : ViewModel() {

    private val _resultado = MutableLiveData<Boolean>()
    val resultado: LiveData<Boolean> = _resultado

    fun crearEspecificacionMonitor(espec: EspecificacionMonitor) {
        viewModelScope.launch {
            try {
                val response = UserNetwork.retrofitEspecificacionMonitor.registrarEspecificacion(espec)
                _resultado.value = response.isSuccessful && response.body() == true
            } catch (e: Exception) {
                _resultado.value = false
            }
        }
    }
}