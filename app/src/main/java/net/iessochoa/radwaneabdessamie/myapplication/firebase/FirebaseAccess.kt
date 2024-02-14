package net.iessochoa.radwaneabdessamie.myapplication.firebase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import net.iessochoa.radwaneabdessamie.myapplication.model.Conferencia
import net.iessochoa.radwaneabdessamie.myapplication.model.Empresa

object FirebaseAccess {
    val TAG = "Practica7"
    //LiveData con los datos empresa
    private val datosEmpresaLiveData = MutableLiveData<Empresa?>()

    //conferencias
    private val listaConferencias = ArrayList<Conferencia>()
    private val conferenciasLiveData = MutableLiveData<List<Conferencia>?>()

    //confrencia iniciada
    private val conferenciaIniciadaLiveData = MutableLiveData<String?>()

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

    fun getDatosConferencaLiveData(): LiveData<List<Conferencia>?>{
        return conferenciasLiveData
    }

    fun buscaConferencias() {
//Solo queremos una lectura. Si ya los hemos cargado no solicitamos los datos
        if (listaConferencias.size == 0) {
            val db = FirebaseFirestore.getInstance()
            //inicamos la colección a buscar
            db.collection(FirebaseContract.ConferenciaEntry.COLLECTION_CONFERENCIAS)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        //listaConferencias.clear()
                        for (conferencia in task.result) {
                            //obtenemos los objetos de clase Conferencia
                            listaConferencias.add(conferencia.toObject(Conferencia::class.java))
                            //actualizamos el LiveData
                            conferenciasLiveData.postValue(listaConferencias)
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.exception)
                    }
                }
        }
    }

    fun getConferenciaIniciadaLiveData():LiveData<String?>{
        return conferenciaIniciadaLiveData
    }


    fun iniciarConferenciaIniciada() {
        val db = FirebaseFirestore.getInstance()
        //leemos el documento registrando la escucha
        val docRef = db.collection(FirebaseContract.COLLECTION_CONF_INICIADA)
            .document(FirebaseContract.ID_DOC_PRINCIPAL_CONF_INICIADA)
        //cuando hay un cambio, se ejecuta el evento
        docRef.addSnapshotListener {snapshot,e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                //recuperamos el campo clave:valor
                val conferenciaIniciada =
                    snapshot.getString(FirebaseContract.CONF_INICIADA_CONFERENCIA)
                //actualizamos el LiveData
                conferenciaIniciadaLiveData.postValue(conferenciaIniciada)
                Log.d(TAG, "Conferencia iniciada: " + snapshot.data)
            } else {
                Log.d(TAG, "Current data: null")
            }
        }
    }


}