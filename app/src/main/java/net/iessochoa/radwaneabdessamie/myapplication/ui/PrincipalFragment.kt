package net.iessochoa.radwaneabdessamie.myapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import net.iessochoa.radwaneabdessamie.myapplication.R
import net.iessochoa.radwaneabdessamie.myapplication.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PrincipalFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    lateinit var auth:FirebaseAuth

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

    }

    fun inicioUsuario(){
        auth=FirebaseAuth.getInstance()
        val userFireBase=auth.currentUser
        binding.tvUsuario.text= "${userFireBase?.displayName} - ${userFireBase?.email}"
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}