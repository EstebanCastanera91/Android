package com.dev.gestionedificios.miCuenta

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MiCuentaViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Mi cuenta"
    }
    val text: LiveData<String> = _text
}
