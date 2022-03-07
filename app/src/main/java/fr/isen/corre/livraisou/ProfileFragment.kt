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
        //setUserInformation()
        val database = Firebase.database

        val user = Firebase.auth.currentUser
        user?.let {
            val uid = user.uid
            val userRef = database.getReference(uid)
            // Read from the database
            userRef.addValueEventListener(object: ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    val dataUser = snapshot.getValue<User>()
                    if (dataUser != null) {
                        setUserInformation(dataUser)
                    }
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
        binding.btnUpdate.setOnClickListener {
            changeActivityToAccount()
        }
        binding.btnLogout.setOnClickListener {
            changeActivityToLogout()
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
    private fun changeActivityToAccount() {
        val intent = Intent (activity, AccountActivity::class.java)
        startActivity(intent)
    }
    private fun changeActivityToLogout() {

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
    private fun setUserInformation(user: User) {
        binding.editName.setText(user.name)
       // binding.editsurname.setText(user.surname)
       // binding.editName.setText(user.name)

    }
}