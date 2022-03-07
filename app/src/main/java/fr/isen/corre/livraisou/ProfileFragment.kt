package fr.isen.corre.livraisou

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import fr.isen.corre.livraisou.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private  lateinit var binding: FragmentProfileBinding
    val TAG = "ProfileFragment"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val database = Firebase.database
        val userRef = database.getReference("user")
        val user = Firebase.auth.currentUser
        user?.let {
            val uid = user.uid
            // Read from the database
            userRef.addValueEventListener(object: ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    val value = snapshot.getValue<User>()


                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w(TAG, "Failed to read value.", error.toException())
                }

            })
        }
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

        binding.pastOrdersRedirect.setOnClickListener {
            changeActivityToPastOrders()
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
    private fun changeActivityToPastOrders() {
        val intent = Intent (activity, PastOrdersActivity::class.java)
        startActivity(intent)
    }
    private fun setUserInformation() {

    }
}