package net.iessochoa.radwaneabdessamie.myapplication.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import net.iessochoa.radwaneabdessamie.myapplication.R
import net.iessochoa.radwaneabdessamie.myapplication.adapter.MensajeAdapter
import net.iessochoa.radwaneabdessamie.myapplication.databinding.FragmentFirstBinding
import net.iessochoa.radwaneabdessamie.myapplication.model.Conferencia
import net.iessochoa.radwaneabdessamie.myapplication.model.Mensaje
import net.iessochoa.radwaneabdessamie.myapplication.viewmodel.AppViewModel
import java.text.DateFormat
import java.util.Locale

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PrincipalFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    lateinit var auth:FirebaseAuth
    private  val viewModel: AppViewModel by activityViewModels()
    private  var listaConferencias:List<Conferencia>?=null
    private var chat :List<Mensaje>?=null
    lateinit var mensajeAdapter: MensajeAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inicioUsuario()
        iniciaSpinnerConferencia()
        iniciaIconoClick()
        inciaTvConferencia()
        iniciaEnvioMensajes()
        iniciaRecyclerView()
        viewModel.chatLiveData.observe(viewLifecycleOwner,Observer<List<Mensaje>?>{lista->
            mensajeAdapter.setLista(lista!!)
        })

    }

    fun inicioUsuario(){
        auth=FirebaseAuth.getInstance()
        val userFireBase=auth.currentUser
        binding.tvUsuario.text= "${userFireBase?.displayName} - ${userFireBase?.email}"
    }

    private fun cargaSpinner() {
        val lista = ArrayList<String?>()
        listaConferencias?.forEach(){lista.add(it.nombre)}
        //El array ArrayAdapter nos permite cargar un array en el spinner
        val adaptador = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            lista
        )
        binding.spnConferencias.onItemSelectedListener= object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position == 0){
                    viewModel.buscaChat("Android")
                }else{
                    viewModel.buscaChat("iOS")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        iniciaMensajes()
        adaptador.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        binding.spnConferencias.setAdapter(adaptador)
    }

    private fun iniciaRecyclerView(){
        mensajeAdapter = MensajeAdapter()

        with(binding.rvMensajes){
            layoutManager = LinearLayoutManager(activity)

            adapter = mensajeAdapter
        }

    }

    private fun iniciaMensajes(){
        viewModel.chatLiveData.observe(viewLifecycleOwner){ mensajes ->
            if (mensajes != null){
                chat =  mensajes
            }
        }
    }

    private fun iniciaSpinnerConferencia(){
        viewModel.conferenciaLiveData.observe(viewLifecycleOwner) { conferencias ->
            if (conferencias != null) {
                listaConferencias = conferencias
                cargaSpinner()
            }
        }
        viewModel.buscaConferencias()
    }

    private fun inciaTvConferencia(){
        viewModel.iniciarConferenicaIniciada()
        viewModel.conferenciaIniciadaLiveData.observe(viewLifecycleOwner){conferencia ->
            if (conferencia != null){
                binding.tvConferencia.text ="Conferencia Iniciada: " + conferencia
            }

        }
    }

    /**
     * Método de extensión para ocultar el teclado.
     */
    fun Context.ocultarTeclado(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun iniciaEnvioMensajes() {
        binding.btEnviar.setOnClickListener(){
            if(!binding.etMensaje.text.toString().isNullOrEmpty()){
                //usuario que envia el mensaje
                val usr=FirebaseAuth.getInstance().currentUser
                val nombre=if(usr?.displayName.isNullOrEmpty())
                    usr?.email?:"desconocido"
                else
                    usr?.displayName
                //mensaje
                val mensaje= Mensaje(nombre,
                    binding.etMensaje.text.toString())
                //conferencia actual en spinner
                val posConferencia=binding.spnConferencias.selectedItemPosition
                val conferencia=listaConferencias?.get(posConferencia)?.id?:"ninguna"
                //enviamos el mensaje
                viewModel.enviarMensajeChat(conferencia,mensaje)
                //ocultamos el teclado
                requireContext().ocultarTeclado(binding.etMensaje)
                //vaciamos el editText
                binding.etMensaje.text.clear()
            }
            (binding.rvMensajes.layoutManager as LinearLayoutManager).scrollToPosition(chat!!.size-1)
        }

    }


    private fun mostrarDatosConferencia(conferencia: Conferencia){
        val fecha :String = DateFormat
                .getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
                .format(conferencia.fecha)

        AlertDialog.Builder(activity as Context)
            .setTitle("Datos Conferencia")
            .setMessage("${conferencia.nombre}\n" +
                    "Fecha: $fecha\n" +
                    "Horario: ${conferencia.horario}\n" +
                    "Sala: ${conferencia.sala}")
            .setPositiveButton(android.R.string.ok){v,_->
                v.dismiss()
            }.show()
    }

    private fun iniciaIconoClick(){
        binding.icVer.setOnClickListener {
            var conferencia : Conferencia = listaConferencias!!.get(binding.spnConferencias.selectedItemPosition)
            mostrarDatosConferencia(conferencia)
        }
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}