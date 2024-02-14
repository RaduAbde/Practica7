package net.iessochoa.radwaneabdessamie.myapplication.ui

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import net.iessochoa.radwaneabdessamie.myapplication.R
import net.iessochoa.radwaneabdessamie.myapplication.databinding.FragmentFirstBinding
import net.iessochoa.radwaneabdessamie.myapplication.model.Conferencia
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
        adaptador.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        binding.spnConferencias.setAdapter(adaptador)
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