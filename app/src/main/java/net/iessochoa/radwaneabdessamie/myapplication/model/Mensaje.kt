package net.iessochoa.radwaneabdessamie.myapplication.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class Mensaje(
    val usuario:String?=null,
    val body:String?=null,
    //@ServerTimestamp permite que sea el servidor el que asigne la hora al crear el  documento
    @ServerTimestamp
    val fechaCreacion: Date?=null
)
