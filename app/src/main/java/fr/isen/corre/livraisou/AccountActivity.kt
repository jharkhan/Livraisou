package fr.isen.corre.livraisou

import android.app.Dialog
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Window
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import fr.isen.corre.livraisou.databinding.ActivityAccountBinding

class AccountActivity : AppCompatActivity() {

    val TAG = "AccountActivity"
    private lateinit var binding: ActivityAccountBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var imageUri: Uri
    private lateinit var dialog : Dialog
    override fun onCreate(savedInstanceState: Bundle?,inflater: LayoutInflater) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate( layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        val uid =auth.currentUser?.uid
        databaseReference = FirebaseDatabase.getInstance().getReference("Users"){
            binding.btnSave.setOnClickListener{

                showProgressBar()
                val firstName = binding.firstName.text.toString()
                val lastName = binding.fullName.text.toString()
                val phoneNumber = binding.phoneNumber.text.toString()
                val email = binding.email.text.toString()
                val location = binding.location.text.toString()

                val user = User(firstName,lastName,phoneNumber,email,location)

                if (uid !=  null){
                    // User is signed in
                    databaseReference.child(uid).setValue(user).addOnCompleteListener{
                        if(it.isSuccessful){
                            uploadProfilePic()
                        }else{
                            hideProgressBar()
                            Toast.makeText(this@AccountActivity, "Failed to update profile",Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    // No user is signed in
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }


            }
        }




        val user = Firebase.auth.currentUser
        val newPassword = "SOME-SECURE-PASSWORD"
        val emailAddress = "user@example.com"
        user?.let {
            // Name, email address, and profile photo Url
            val name = user.displayName
            val email = user.email
            val photoUrl = user.photoUrl

            // Check if user's email is verified
            val emailVerified = user.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            val uid = user.uid
        }

        if (user != null) {
            // User is signed in

           val profileUpdates = userProfileChangeRequest {
                displayName = "Jane Q. User"
                photoUri = Uri.parse("https://example.com/jane-q-user/profile.jpg")
            }
            //defind  email adresse
            user!!.updateEmail("user@example.com")
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "User email address updated.")
                    }
                }
            //send email verif
            user!!.sendEmailVerification()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "Email sent.")
                    }
                }
            //update password
            user!!.updatePassword(newPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "User password updated.")
                    }
                }

            //update profil
            user!!.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "User profile updated.")
                    }
                }
            //send an email to reset password !!!!Mot de passe oublié
            Firebase.auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "Email sent.")
                    }
                }
        } else {
            // No user is signed in
            Toast.makeText(
                baseContext, "Authentication failed.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    private fun uploadProfilePic(){
        storageReference= FirebaseStorage.getInstance().getReference("Users/"+auth.currentUser?.uid)
        storageReference.putFile(imageUri).addOnSuccessListener {
            hideProgressBar()
            Toast.makeText(this@AccountActivity, "Profile succesfuly updated",Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            hideProgressBar()
            Toast.makeText(this@AccountActivity, "Failed to update the image",Toast.LENGTH_SHORT).show()
        }

    }
    private fun showProgressBar(){
        dialog = Dialog(this@AccountActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
    private fun hideProgressBar(){
        dialog.dismiss()
    }
}





