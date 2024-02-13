package net.iessochoa.radwaneabdessamie.myapplication.model

import android.net.Uri
import com.google.firebase.firestore.GeoPoint

data class Empresa (
    val nombre: String? = null,
    val direccion: String? = null,
    val telefono: String? = null,
    val localizacion: GeoPoint? = null
){
    val uriTelefono: Uri
        get() {
            return Uri.parse("tel:$telefono")
        }
    val uriLocalizacion:Uri
        get(){
            return Uri.parse("geo:" + localizacion?.latitude + "," +
                    localizacion?.longitude)
        }
}
