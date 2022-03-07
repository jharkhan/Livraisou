package fr.isen.corre.livraisou

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.isen.corre.livraisou.databinding.FragmentProfileBinding



// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    private  lateinit var binding: FragmentProfileBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listenClick()
    }

    private fun listenClick() {
        binding.btnLogin.setOnClickListener {
            changeActivityToLogin()
        }

        binding.btnRegister.setOnClickListener {
            changeActivityToRegister()
        }

        binding.btnMap.setOnClickListener {
            changeActivityToMap()
        }
    }

    private fun changeActivityToLogin() {
            val intent = Intent (activity, LoginActivity::class.java)
            startActivity(intent)
    }

    private fun changeActivityToRegister() {
        val intent = Intent (activity, RegisterActivity::class.java)
        startActivity(intent)
    }
    private fun changeActivityToMap() {
        val intent = Intent (activity, MapsActivity::class.java)
        startActivity(intent)
    }
}