package com.dev.gestionedificios.misUnidades

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MisUnidadesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Mis unidades"
    }
    val text: LiveData<String> = _text
}