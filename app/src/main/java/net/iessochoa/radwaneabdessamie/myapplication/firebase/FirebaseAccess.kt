package net.iessochoa.radwaneabdessamie.myapplication.firebase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import net.iessochoa.radwaneabdessamie.myapplication.model.Conferencia
import net.iessochoa.radwaneabdessamie.myapplication.model.Empresa
import net.iessochoa.radwaneabdessamie.myapplication.model.Mensaje

object FirebaseAccess {
    val TAG = "Practica7"
    //LiveData con los datos empresa
    private val datosEmpresaLiveData = MutableLiveData<Empresa?>()

    //conferencias
    private val listaConferencias = ArrayList<Conferencia>()
    private val conferenciasLiveData = MutableLiveData<List<Conferencia>?>()

    //confrencia iniciada
    private val conferenciaIniciadaLiveData = MutableLiveData<String?>()

    private var chat = ArrayList<Mensaje>()
    private val chatLiveData = MutableLiveData<List<Mensaje>?>()

    private var registroChat:ListenerRegistration? = null

    fun buscaChat(conferencia: String) {
        //suponemos que cambiamos de Conferencia y leemos otra, por lo que
        //vaciamos la lista con mensajes de otra conferencia
        chat.clear()
        //cerramos la conexión de otra conferencia si la hay
        registroChat?.remove()
        registroChat = FirebaseFirestore.getInstance()
            //coleccion conferencias
            .collection(FirebaseContract.ConferenciaEntry.COLLECTION_CONFERENCIAS)
//documento: conferencia actual
            .document(conferencia)
//colección chat de la conferencia
            .collection(FirebaseContract.COLLECTION_CHAT)
//obtenemos la lista ordenada por fecha
            .orderBy(
                FirebaseContract.CHAT_FECHA_CREACION,
                Query.Direction.ASCENDING
            )//con el resultado
            .addSnapshotListener() { snapshots, e ->
                if (e != null) {
                    Log.w(TAG, "listen:error", e)
                    return@addSnapshotListener
                }
                //creamos una lista con los mensajes nuevos añadidos
                snapshots!!.documentChanges.forEach() {
                    when (it.type) {
                        //con los documentos añadidos, creamos la lista y actualizamos el LiveData
                                DocumentChange.Type.ADDED -> {
                            val mensaje = it.document.toObject(Mensaje::class.java)
                            chat.add(mensaje)
                            chatLiveData.postValue(chat)
                        }
                        //Si tenemos que actuar ante modificaciones o borrado
                        DocumentChange.Type.MODIFIED -> {}
                        DocumentChange.Type.REMOVED -> {}
                    }
                }
            }
    }

    fun getChatLiveData():LiveData<List<Mensaje>?>
    {
        return chatLiveData
    }



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

    fun enviarMensajeChat(conferencia:String,mensaje: Mensaje) {
        val db = FirebaseFirestore.getInstance()
        //coleccion conferencia
        db.collection(FirebaseContract.ConferenciaEntry.COLLECTION_CONFERENCIAS)
            //Documento en el que añadimos el mensaje
            .document(conferencia)
            //subcolección
            .collection(FirebaseContract.COLLECTION_CHAT) //añadimos el mensaje  nuevo
            //añade documento, generando su id el sistema
            .add(mensaje)
    }



}