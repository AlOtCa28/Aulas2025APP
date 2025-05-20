package com.example.aulas2025app.JEFE.JefeProfesores

import API.UserNetwork
import Modelo.Usuario.Usuario
import Modelos.Aulas.Aula
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response

class ProfesoresViewModel : ViewModel() {
    private val _myResponse = MutableLiveData<List<Usuario>>()
    val myResponse: LiveData<List<Usuario>> get() = _myResponse

    private val _myResponseList = MutableLiveData<List<Usuario>>()
    val myResponseList: LiveData<List<Usuario>> get() = _myResponseList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _resOperacion = MutableLiveData<Boolean>()
    val resOperacion: LiveData<Boolean> get() = _resOperacion

    private val _errorCode = MutableLiveData<Int?>()
    val errorCode: LiveData<Int?> get() = _errorCode

    fun getUsuariosVM() {
        viewModelScope.launch {
            _isLoading.value = true
            var response: Response<MutableList<Usuario>> = UserNetwork.retrofit.listarUsuarios()

            if (response.isSuccessful) {
                _myResponseList.value = response.body()
            } else {
                _myResponseList.value = emptyList()
                _errorCode.value = response.code()
            }
            _isLoading.value = false
        }
    }


    fun registrarUsuarioVM(usuario: Usuario) {
        viewModelScope.launch {
            _isLoading.value = true
            var response: Response<Boolean> = UserNetwork.retrofit.registrarUsuario(usuario)

            if (response.isSuccessful) {
                println("Respuesta del servidor: ${response.body()}")
                _resOperacion.value = true
            } else {
                _resOperacion.value = false
                _errorCode.value = response.code()
                println("Error Code: ${response.code()}, Error Body: ${response.errorBody()?.string()}")
            }

            _isLoading.value = false
        }
    }
}