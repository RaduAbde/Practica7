package net.iessochoa.radwaneabdessamie.myapplication.firebase

class FirebaseContract {

    companion object{
        val COLLECTION_CONF_INICIADA="ConferenciaIniciada"
        val ID_DOC_PRINCIPAL_CONF_INICIADA="conferencia_iniciada"
        val CONF_INICIADA_CONFERENCIA="conferencia"
    }

    class EmpresaEntry{
        companion object {
            //nombre de la colección
            val COLLECTION_EMPRESA= "empresas"
            //id del documento que necesitamos de los datos de la empresa
            val ID_DOC_PRINCIPAL_EMPRESA = "ochoa"


        }
    }

    class ConferenciaEntry{
        companion object{
            //nombre de la colección
            val COLLECTION_EMPRESA= "empresas"
            //id del documento que necesitamos de los datos de la empresa
            val ID_DOC_PRINCIPAL_EMPRESA = "ochoa"
            val COLLECTION_CONFERENCIAS = "conferencias"
        }
    }


}
