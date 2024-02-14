package net.iessochoa.radwaneabdessamie.myapplication.model

import java.util.Date

data class Conferencia(
    val encurso : Boolean? = null,
    val fecha : Date? = null,
    val horario : String? = null,
    val id : String? = null,
    val nombre : String? = null,
    val plazas : Int? = null,
    val ponente : String? = null,
    val sala : String? = null
) {
}