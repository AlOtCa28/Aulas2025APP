package com.example.aulas2025app.JEFE.JefeProfesores.DetallesUsuario

import API.UserNetwork
import Modelo.Usuario.Usuario
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetalleUsuarioViewModel : ViewModel() {

    private val _usuario = MutableLiveData<Usuario?>()
    val usuario: LiveData<Usuario?> = _usuario

    private val usuarioAPI = UserNetwork.retrofit  // Aquí usas tu singleton


    fun setUsuario(usuario: Usuario) {
        _usuario.value = usuario
    }

    fun cargarUsuarioPorNombre(nombre: String) {
        viewModelScope.launch {
            try {
                val response = usuarioAPI.obtenerUsuarioPorNombre(nombre)
                if (response.isSuccessful) {
                    _usuario.postValue(response.body())
                } else {
                    _usuario.postValue(null)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _usuario.postValue(null)
            }
        }
    }

    fun guardarUsuario(usuario: Usuario, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val response = usuarioAPI.actualizarUsuario(usuario)
                withContext(Dispatchers.Main) {
                    onResult(response.isSuccessful && (response.body() == true))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    onResult(false)
                }
            }
        }
    }
}
