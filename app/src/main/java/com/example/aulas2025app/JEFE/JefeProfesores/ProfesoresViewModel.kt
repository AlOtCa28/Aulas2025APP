package com.example.aulas2025app.JEFE.JefeProfesores

import API.UserNetwork
import Modelo.Usuario.Usuario
import Modelos.Aulas.Aula
import android.util.Log
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

    private val _resultadoBorrar = MutableLiveData<Pair<Boolean, Usuario?>>()
    val resultadoBorrar: LiveData<Pair<Boolean, Usuario?>> = _resultadoBorrar

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


    fun borrarUsuario(usuario: Usuario) {
        viewModelScope.launch {
            try {
                val response = UserNetwork.retrofit.eliminarUsuario(usuario.id ?: 0)
                Log.d("BorrarUsuario", "Response code: ${response.code()}, success: ${response.isSuccessful}")
                _resultadoBorrar.postValue(Pair(response.isSuccessful, usuario))
            } catch (e: Exception) {
                Log.e("BorrarUsuario", "Error al borrar usuario", e)
                _resultadoBorrar.postValue(Pair(false, usuario))
            }
        }
    }


}