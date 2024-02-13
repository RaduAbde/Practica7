package net.iessochoa.radwaneabdessamie.myapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import net.iessochoa.radwaneabdessamie.myapplication.R
import net.iessochoa.radwaneabdessamie.myapplication.databinding.FragmentDatosEmpresaBinding

import net.iessochoa.radwaneabdessamie.myapplication.model.Empresa
import net.iessochoa.radwaneabdessamie.myapplication.viewmodel.AppViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class DatosEmpresaFragment : Fragment() {

    private var _binding: FragmentDatosEmpresaBinding? = null
    private val viewModel: AppViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDatosEmpresaBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.obtenDatosEmpresa()
        viewModel.datosEmpresaLiveData.observe(viewLifecycleOwner) { empresa ->
            if (empresa != null) inicaDatosEmpresa(empresa)
        }
    }

    private fun inicaDatosEmpresa(empresa: Empresa){
        binding.tvNombreEmpresa.text = empresa.nombre
        binding.tvDireccionEmpresa.text = "Dirección: ${empresa.direccion}"
        binding.tvNumeroEmpresa.text = "Numero: ${empresa.telefono}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}