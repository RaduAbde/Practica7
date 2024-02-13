package net.iessochoa.radwaneabdessamie.myapplication.repository

import net.iessochoa.radwaneabdessamie.myapplication.firebase.FirebaseAccess

object Repository {

    fun obtenDatosEmpresa()=FirebaseAccess.obtenDatosEmpresa()

    fun getDatosEmpresaLiveData()=FirebaseAccess.getDatosEmpresaLiveData()

}