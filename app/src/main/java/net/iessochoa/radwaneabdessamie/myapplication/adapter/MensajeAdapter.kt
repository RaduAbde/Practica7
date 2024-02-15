package net.iessochoa.radwaneabdessamie.myapplication.adapter

import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import net.iessochoa.radwaneabdessamie.myapplication.databinding.FragmentDatosEmpresaBinding
import net.iessochoa.radwaneabdessamie.myapplication.databinding.ItemMensajeBinding
import net.iessochoa.radwaneabdessamie.myapplication.model.Mensaje

class MensajeAdapter : RecyclerView.Adapter<MensajeAdapter.MensajeViewHolder>() {

    var listaMensajes: List<Mensaje>?=null
    lateinit var auth: FirebaseAuth



    fun setLista(lista:List<Mensaje>){
        listaMensajes = lista
        notifyDataSetChanged()
    }


    inner class MensajeViewHolder(val binding: ItemMensajeBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MensajeViewHolder {

            val binding = ItemMensajeBinding
                .inflate(LayoutInflater.from(parent.context),parent,false)
            return MensajeViewHolder(binding)

    }

    override fun getItemCount(): Int = listaMensajes?.size?:0

    override fun onBindViewHolder(holder: MensajeViewHolder, position: Int) {
        var mensaje:Mensaje = listaMensajes!!.get(position)
        with(holder){
            with( mensaje){
                auth=FirebaseAuth.getInstance()
                val userFireBase=auth.currentUser?.displayName
                binding.tvUsuarioMensaje.text = usuario
                binding.tvCuerpoMensaje.text= body
                if (mensaje!!.usuario!!.equals(userFireBase)){
                    binding.cvItem.setBackgroundColor(Color.YELLOW)
                    binding.lyItem.gravity=Gravity.START
                }else{
                    binding.cvItem.setBackgroundColor(Color.WHITE)
                    binding.lyItem.gravity=Gravity.END
                }
            }
        }




    }
}