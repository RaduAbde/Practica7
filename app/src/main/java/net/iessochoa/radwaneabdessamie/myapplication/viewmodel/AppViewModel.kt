package net.iessochoa.radwaneabdessamie.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import net.iessochoa.radwaneabdessamie.myapplication.model.Mensaje
import net.iessochoa.radwaneabdessamie.myapplication.repository.Repository

class AppViewModel:ViewModel() {

    val datosEmpresaLiveData=Repository.getDatosEmpresaLiveData()
    val conferenciaLiveData=Repository.getConferenciasLiveData()
    val conferenciaIniciadaLiveData=Repository.getConferenciaIniciadaLiveData()
    val chatLiveData=Repository.getChatLiveData()

    fun obtenDatosEmpresa()=Repository.obtenDatosEmpresa()

    fun buscaConferencias()=Repository.buscaConferencias()

    fun iniciarConferenicaIniciada()=Repository.inciarConferenciaIniciada()

    fun enviarMensajeChat(conferencia:String,mensaje: Mensaje)=Repository.enviarMensajeChat(conferencia,mensaje)

    fun buscaChat(conferencia: String)=Repository.buscaChat(conferencia)

}