
package fr.isen.corre.livraisou

import android.app.AlertDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import fr.isen.corre.livraisou.databinding.FragmentProfileBinding
import fr.isen.corre.livraisou.AccountActivity
import java.io.File
import com.google.firebase.storage.StorageReference

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
    private lateinit var storageReference: StorageReference
    private  lateinit var uid: String
    private var imageUri: Uri? = null
    val TAG = "ProfileFragment"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
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
        }
        else {
            binding.btnLogout.visibility = View.INVISIBLE
        }
        user?.let {
            uid = it.uid
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
        getUserProfilePic()
    }

    private fun listenClick() {
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
        //val phoneNumber = dataSnapshot.child("phoneNum").value
        val address = dataSnapshot.child("location").value
        binding.userName.setText(firstName.toString())
        binding.userLastname.setText(lastName.toString())
        //binding.userPhone.setText(phoneNumber.toString())
       // binding.userEmail.setText(user.email.toString())
        binding.userAddress.setText(address.toString())
    }

    private fun getUserProfilePic() {
        storageReference = FirebaseStorage.getInstance().getReference().child("profilPic/$uid.jpg")
        val localFile = File.createTempFile("tempImage", ".jpg")
        storageReference.getFile(localFile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            binding.profilPic.setImageBitmap(bitmap)
            Toast.makeText(this.context, "Picture Retrieved", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this.context, "Failed to retrieve image", Toast.LENGTH_SHORT)
                .show()
        }

    }
}