package com.example.aulas2025app.JEFE.JefeDispositivos.Especificaciones.EspecificacionImpresora

import API.UserNetwork
import Modelos.EspecificacioensImpresora.EspecificacionImpresora
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class EspecificacionImpresoraViewModel : ViewModel() {

    private val _resultado = MutableLiveData<Boolean>()
    val resultado: LiveData<Boolean> = _resultado

    fun crearEspecificacionImpresora(espec: EspecificacionImpresora) {
        viewModelScope.launch {
            try {
                val response = UserNetwork.retrofitEspecificacionImpresora.registrarEspecificacion(espec)
                _resultado.value = response.isSuccessful && response.body() == true
            } catch (e: Exception) {
                _resultado.value = false
            }
        }
    }
}
