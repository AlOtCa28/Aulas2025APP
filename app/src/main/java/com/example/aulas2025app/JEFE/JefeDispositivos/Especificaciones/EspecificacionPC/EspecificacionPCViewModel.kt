package com.example.aulas2025app.JEFE.JefeDispositivos.Especificaciones.EspecificacionPC

import API.UserNetwork
import Modelos.EspecificacionesPC.EspecificacionPC
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class EspecificacionPCViewModel : ViewModel() {

    val resultado = MutableLiveData<Boolean>()

    fun crearEspecificacionPC(especificacion: EspecificacionPC) {
        viewModelScope.launch {
            try {
                val response = UserNetwork.retrofitEspecificacionesPC.registrarEspecificacion(especificacion)
                resultado.value = response.isSuccessful && response.body() == true
            } catch (e: Exception) {
                resultado.value = false
            }
        }
    }
}
