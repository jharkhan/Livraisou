package fr.isen.corre.livraisou

import android.app.Dialog
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import android.view.Window
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth

import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import fr.isen.corre.livraisou.databinding.ActivityAccountBinding
import java.io.File
import android.R
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.icu.number.NumberFormatter.with
import android.os.Build
import android.os.Environment

import android.provider.MediaStore
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*



class AccountActivity : AppCompatActivity() {
    val TAG = "AccountActivity"
    private lateinit var binding: ActivityAccountBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var storageReference: StorageReference
    private var imageUri: Uri? = null
    private lateinit var dialog: Dialog
    private lateinit var uid: String
    private var imageView: ImageView? = null
    private var CAPTURE_PHOTO = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val user = Firebase.auth.currentUser
        val database = Firebase.database
        user?.let {
            showProgressBar()
            uid = it.uid
            val userRef = database.getReference(uid)
            // Read from the database
            userRef.addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.

                    val firstName = snapshot.child("name").value
                    val lastName = snapshot.child("surname").value
                    val phoneNumber = snapshot.child("phoneNum").value
                    val location = snapshot.child("location").value

                    binding.userFirstName.setText(firstName.toString())
                    binding.userLastname.setText(lastName.toString())
                    binding.userPhoneNumber.setText(phoneNumber.toString())
                    binding.userEmail.setText(Firebase.auth.currentUser?.email.toString())
                    binding.userLocation.setText(location.toString())
                    getUserProfilePic()


                }

                override fun onCancelled(error: DatabaseError) {
                    hideProgressBar()
                    Log.w(TAG, "Failed to read value.", error.toException())
                    Toast.makeText(
                        this@AccountActivity,
                        "Failed to get Profile Data",
                        Toast.LENGTH_SHORT
                    ).show()


                }

            })

        }
        binding.profilPic.setOnClickListener {
            openGalleryOrTakePic()


        }
        binding.btnSave.setOnClickListener {

            if (user != null) {
                uploadProfilPic()
            } else {
                // No user is signed in
                Toast.makeText(
                    baseContext, "Authentication failed.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    private fun uploadProfilPic() {
        storageReference = FirebaseStorage.getInstance().getReference().child("profilPic/$uid.jpg")
        imageUri?.let {
            storageReference.putFile(it).addOnSuccessListener {
                hideProgressBar()
                Toast.makeText(
                    this@AccountActivity,
                    "Profile succesfuly updated",
                    Toast.LENGTH_SHORT
                ).show()
            }.addOnFailureListener {
                hideProgressBar()
                Toast.makeText(
                    this@AccountActivity,
                    "Failed to update the image",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    private fun getUserProfilePic() {
        storageReference = FirebaseStorage.getInstance().getReference().child("profilPic/$uid.jpg")
        val localFile = File.createTempFile("tempImage", ".jpg")
        storageReference.getFile(localFile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            binding.profilPic.setImageBitmap(bitmap)
            Toast.makeText(this@AccountActivity, "Picture Retrieved", Toast.LENGTH_SHORT).show()
            hideProgressBar()
        }.addOnFailureListener {
            hideProgressBar()
            Toast.makeText(this@AccountActivity, "Failed to retrieve image", Toast.LENGTH_SHORT)
                .show()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CHOOSE_PHOTO && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData()
            binding.profilPic.setImageURI(imageUri)
            Toast.makeText(
                this@AccountActivity,
                "Picture Retrieved From Gallery",
                Toast.LENGTH_SHORT
            ).show()
        } else if (requestCode == CAPTURE_PHOTO && resultCode == RESULT_OK && data != null) {
            // Create an image file name !!!!NE fonctionne pas !!!!
            //val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
            val tempFile = File.createTempFile("tempImage_${uid}", ".jpg", storageDir)
            val bitmap = BitmapFactory.decodeFile(tempFile.absolutePath)
            binding.profilPic.setImageBitmap(bitmap)

            Toast.makeText(
                this@AccountActivity,
                "Picture Retrieved From Camera",
                Toast.LENGTH_SHORT
            ).show()
            //add it in gallery
        } else {
            Toast.makeText(
                this@AccountActivity,
                "Failed to retrieve profil Pic",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun capturePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(intent, CAPTURE_PHOTO)
    }


    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, CHOOSE_PHOTO)
    }

    companion object {


        private val CHOOSE_PHOTO = 1000;
        private val PERMISSION_CODE = 1001;
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun openGalleryOrTakePic() {
        val alertDialog: AlertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle("Chosissez une photo")
        alertDialog.setMessage("Veuillez prendre une photo depuis:")

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Gallery") { dialog, _ ->
            openGallery()
            dialog.dismiss()
        }
        //Code permettant appelle de la fonction ouvrant l'appareil photo
        //commenté car une fois capturé photo pas sauvegardée
        // alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Camera") {
        //         dialog, _ ->
        //     capturePhoto()
        //     dialog.dismiss()
        // }
        alertDialog.show()
    }

    private fun showProgressBar() {
        dialog = Dialog(this@AccountActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        // dialog.setContentView(R.layout.wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    private fun hideProgressBar() {
        dialog.dismiss()
    }
}



