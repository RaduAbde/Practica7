package net.iessochoa.radwaneabdessamie.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import net.iessochoa.radwaneabdessamie.myapplication.repository.Repository

class AppViewModel:ViewModel() {

    val datosEmpresaLiveData=Repository.getDatosEmpresaLiveData()
    val conferenciaLiveData=Repository.getConferenciasLiveData()
    val conferenciaIniciadaLiveData=Repository.getConferenciaIniciadaLiveData()

    fun obtenDatosEmpresa()=Repository.obtenDatosEmpresa()

    fun buscaConferencias()=Repository.buscaConferencias()

    fun iniciarConferenicaIniciada()=Repository.inciarConferenciaIniciada()

}