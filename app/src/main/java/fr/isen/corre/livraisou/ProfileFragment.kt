package fr.isen.corre.livraisou

import android.app.AlertDialog
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
            val uid = user.uid
            val userRef = database.getReference(uid)
            // Read from the database
            userRef.addValueEventListener(object: ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    val firstName = snapshot.child("surname").value
                    val lastName = snapshot.child("name").value
                    val phoneNumber = snapshot.child("phoneNum").value
                    binding.userName.setText(firstName.toString())
                    binding.userLastname.setText(lastName.toString())
                    binding.userPhone.setText(phoneNumber.toString())
                    binding.userEmail.setText(it.email.toString())
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
    private fun setUserInformation(user: User) {

        binding.userName.setText(user.name)


       // binding.editsurname.setText(user.surname)
       // binding.editName.setText(user.name)

    }
}


//Log.d(TAG, "dataUser.toString()")
//Log.d(TAG, dataUser.toString())
//if (dataUser != null) {
//    setUserInformation(dataUser)
//}