package fr.isen.corre.livraisou

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
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

        val user = Firebase.auth.currentUser
        if (user != null) {
            Log.d(TAG, user.email.toString())
            binding.btnLogin.visibility = View.INVISIBLE
        }
        else {
            binding.btnLogout.visibility = View.INVISIBLE
        }
        user?.let {
            val uid = it.uid
            val userRef = database.getReference(uid)
            // Read from the database
            userRef.addValueEventListener(object: ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    setUserInformation(snapshot, it)
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
            logout()
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

    private fun logout() {
        val alertDialog: AlertDialog = AlertDialog.Builder(this.context).create()
        alertDialog.setTitle("Se déconnecter ?")
        alertDialog.setMessage("Voulez vous vraiment vous deconnecter ?")

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Oui") {
            dialog, which -> Firebase.auth.signOut()
            binding.btnLogout.visibility = View.INVISIBLE
            binding.btnLogin.visibility = View.VISIBLE
            changeActivityToLogin()
            dialog.dismiss()
        }

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Non") {
            dialog, which ->
            dialog.dismiss()
        }
        alertDialog.show()
    }

    private fun changeActivityToPastOrders() {
        val intent = Intent (activity, PastOrdersActivity::class.java)
        startActivity(intent)
    }

    private fun setUserInformation(dataSnapshot: DataSnapshot, user: FirebaseUser) {
        val firstName = dataSnapshot.child("surname").value
        val lastName = dataSnapshot.child("name").value
        val phoneNumber = dataSnapshot.child("phoneNum").value
        binding.userName.setText(firstName.toString())
        binding.userLastname.setText(lastName.toString())
        binding.userPhone.setText(phoneNumber.toString())
        binding.userEmail.setText(user.email.toString())
    }
}


