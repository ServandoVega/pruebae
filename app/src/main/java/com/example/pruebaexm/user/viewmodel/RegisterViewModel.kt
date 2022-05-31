package com.example.pruebaexm.user.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebaexm.database.model.Register
import com.example.pruebaexm.repository.RegisterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RegisterViewModel: ViewModel() {
    var listRegister = MutableLiveData<List<Register>>()
    var repository: RegisterRepository = RegisterRepository()

    fun createRegister(register: Register){
        viewModelScope.launch(Dispatchers.IO) {
            repository.createRegister(register)
        }
    }
    fun getListRegister(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getListRegister().collect {
                listRegister.postValue(it)
            }
        }
    }
}