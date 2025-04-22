package com.example.aulas2025app.JEFE.JefeAulas

import API.UserNetwork
import Modelos.Aulas.Aula
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
}