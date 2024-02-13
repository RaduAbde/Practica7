package net.iessochoa.radwaneabdessamie.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import net.iessochoa.radwaneabdessamie.myapplication.repository.Repository

class AppViewModel:ViewModel() {

    val datosEmpresaLiveData=Repository.getDatosEmpresaLiveData()

    fun obtenDatosEmpresa()=Repository.obtenDatosEmpresa()

}