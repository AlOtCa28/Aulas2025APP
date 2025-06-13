package com.example.aulas2025app.JEFE.JefeAulas

import API.UserNetwork
import Modelos.Aulas.Aula
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response

class FragmentoAulasViewModel : ViewModel() {

    private val _myResponse = MutableLiveData<List<Aula>>()
    val myResponse: LiveData<List<Aula>> get() = _myResponse

    private val _myResponseList = MutableLiveData<List<Aula>>()
    val myResponseList: LiveData<List<Aula>> get() = _myResponseList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _resOperacion = MutableLiveData<Boolean>()
    val resOperacion: LiveData<Boolean> get() = _resOperacion

    private val _errorCode = MutableLiveData<Int?>()
    val errorCode: LiveData<Int?> get() = _errorCode

    private val _resultado = MutableLiveData<Boolean>()
    val resultado: LiveData<Boolean> = _resultado

    fun getAulasVM() {
        viewModelScope.launch {
            _isLoading.value = true
            var response: Response<MutableList<Aula>> = UserNetwork.retrofitAulas.listarAulas()

            if (response.isSuccessful) {
                _myResponseList.value = response.body()
            } else {
                _myResponseList.value = emptyList()
                _errorCode.value = response.code()
            }
            _isLoading.value = false
        }
    }


    fun crearAula(aula: Aula) {
        viewModelScope.launch {
            try {
                val response = UserNetwork.retrofitAulas.registrarAula(aula)
                _resultado.value = response.isSuccessful && response.body() == true
            } catch (e: Exception) {
                _resultado.value = false
            }
        }
    }

    fun eliminarAula(id: Int) {
        viewModelScope.launch {
            try {
                Log.d("FragmentoAulasVM", "Intentando eliminar aula con id: $id")
                val response = UserNetwork.retrofitAulas.eliminarAula(id)
                Log.d("FragmentoAulasVM", "Respuesta retrofit: ${response.code()} - ${response.message()}")
                Log.d("FragmentoAulasVM", "Body: ${response.body()}")
                _resOperacion.value = response.isSuccessful && response.body() == true
            } catch (e: Exception) {
                Log.e("FragmentoAulasVM", "Error al eliminar aula", e)
                _resOperacion.value = false
            }
        }
    }

}