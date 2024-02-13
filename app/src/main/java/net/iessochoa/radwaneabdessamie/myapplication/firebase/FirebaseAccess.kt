package net.iessochoa.radwaneabdessamie.myapplication.firebase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import net.iessochoa.radwaneabdessamie.myapplication.model.Empresa

object FirebaseAccess {
    val TAG = "Practica7"
    //LiveData con los datos empresa
    private val datosEmpresaLiveData = MutableLiveData<Empresa?>()
    /**
     * Permite buscar los datos de empresa en Firebase.
     * Realiza una sola lectura de un documento sin actualización en tiempo real
     */
    fun obtenDatosEmpresa() {
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection(FirebaseContract.EmpresaEntry.COLLECTION_EMPRESA)
            .document(FirebaseContract.EmpresaEntry.ID_DOC_PRINCIPAL_EMPRESA)
        docRef.get().addOnSuccessListener { documentSnapshot -> //leemos los datos
            val empresa =
                documentSnapshot.toObject<Empresa>(Empresa::class.java)//?:empresaSinDatos
            //actualizamos los datos de la empresa
            datosEmpresaLiveData.postValue(empresa)
        }
    }
    //LiveData con los datos de la empresa
    fun getDatosEmpresaLiveData(): LiveData<Empresa?> {
        return datosEmpresaLiveData
    }

}