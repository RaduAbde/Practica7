package net.iessochoa.radwaneabdessamie.myapplication.repository

import net.iessochoa.radwaneabdessamie.myapplication.firebase.FirebaseAccess
import net.iessochoa.radwaneabdessamie.myapplication.model.Mensaje

object Repository {

    fun obtenDatosEmpresa()=FirebaseAccess.obtenDatosEmpresa()

    fun getDatosEmpresaLiveData()=FirebaseAccess.getDatosEmpresaLiveData()

    //conferencias
    fun getConferenciasLiveData()=FirebaseAccess.getDatosConferencaLiveData()

    fun buscaConferencias()=FirebaseAccess.buscaConferencias()

    fun getConferenciaIniciadaLiveData()=FirebaseAccess.getConferenciaIniciadaLiveData()

    fun inciarConferenciaIniciada()=FirebaseAccess.iniciarConferenciaIniciada()

    fun enviarMensajeChat(conferecia:String, mensaje: Mensaje) = FirebaseAccess.enviarMensajeChat(conferecia,mensaje)

    fun getChatLiveData()=FirebaseAccess.getChatLiveData()

    fun buscaChat(conferecia: String)=FirebaseAccess.buscaChat(conferecia)

}