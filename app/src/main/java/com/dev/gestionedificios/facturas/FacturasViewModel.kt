package com.dev.gestionedificios.facturas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FacturasViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Facturas"
    }
    val text: LiveData<String> = _text
}