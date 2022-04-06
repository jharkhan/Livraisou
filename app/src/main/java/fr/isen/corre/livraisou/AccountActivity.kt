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
import android.provider.MediaStore
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.squareup.picasso.Picasso


class AccountActivity : AppCompatActivity() {


    val TAG = "AccountActivity"
    private lateinit var binding: ActivityAccountBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var storageReference: StorageReference
    private  var imageUri: Uri?=null
    private lateinit var dialog : Dialog
    private lateinit var uid :String
    private  var imageView: ImageView? =null
    private var CAPTURE_PHOTO=1
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val user = Firebase.auth.currentUser
        val database = Firebase.database
        user?.let {
            showProgressBar()
            //val photoUrl = user.photoUrl

            uid = it.uid
            val userRef = database.getReference(uid)
            // Read from the database
            userRef.addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.

                    // val photoURL = snapshot.child("profilPicURL").value
                    val firstName = snapshot.child("name").value
                    val lastName = snapshot.child("surname").value
                    val phoneNumber = snapshot.child("phoneNum").value
                    val photoURL= snapshot.child("profilPicURL").value
                    val location = snapshot.child("location").value

                    binding.userFirstName.setText(firstName.toString())
                    binding.userLastname.setText(lastName.toString())
                    binding.userPhoneNumber.setText(phoneNumber.toString())
                    binding.userEmail.setText(Firebase.auth.currentUser?.email.toString())
                    // binding.profilPic.setImageDrawable(photoURL as Drawable?)
                    binding.userLocation.setText(location.toString())
                    getUserProfilePic()

                    // if(photoURL?.isNotEmpty() == true) {
                    //     Picasso.get().load(photoURL).placeholder(R.drawable.good_food).into(binding.photo)
                    // Picasso.with(this).load(imageUri).into(profilPic)
                    // }

                }

                override fun onCancelled(error: DatabaseError) {
                    hideProgressBar()
                    Log.w(TAG, "Failed to read value.", error.toException())
                    Toast.makeText( this@AccountActivity,"Failed to get Profile Data",Toast.LENGTH_SHORT).show()


                }

            })

        }
        binding.profilPic.setOnClickListener{
            //check permission at runtime
           /* val checkSelfPermission = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (checkSelfPermission != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
            }
            else{*/
                openGalleryOrTakePic()
           // }

        }
        binding.btnSave.setOnClickListener {







            if (user != null) {
                // User is signed in
             //   databaseReference.child(uid).setValue(user).addOnCompleteListener {
             //       if (it.isSuccessful) {
             //           uploadProfile()
               //     } else {
               //         hideProgressBar()
                //        Toast.makeText(
                //            this@AccountActivity,
                //            "Failed to update profile",
               //             Toast.LENGTH_SHORT
              //          ).show()
              //      }
             //   }


                //send email verif
               // user!!.sendEmailVerification()
               //     .addOnCompleteListener { task ->
               //         if (task.isSuccessful) {
              //              Log.d(TAG, "Email sent.")
               //         }
              //      }
                //update password
                // user!!.updatePassword(userNewPassword)
                //   .addOnCompleteListener { task ->
                //       if (task.isSuccessful) {
                //          Log.d(TAG, "User password updated.")
                //       }
                //   }

                //send an email to reset password !!!!Mot de passe oublié
                // Firebase.auth.sendPasswordResetEmail(email)
                //     .addOnCompleteListener { task ->
                //         if (task.isSuccessful) {
                //            Log.d(TAG, "Email sent.")
                //       }
                //   }
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

    override fun onActivityReenter(resultCode: Int, data: Intent?) {
        super.onActivityReenter(resultCode, data)
        if(resultCode== RESULT_OK && data !=null){
            imageUri=data.getData()
            binding.profilPic.setImageURI(imageUri)
        }
    }
    private fun uploadProfilPic(){
       // storageReference= FirebaseStorage.getInstance().getReference("Users/"+auth.currentUser?.uid)
        storageReference= FirebaseStorage.getInstance().getReference().child("profilPic/userPic.jpg")
        imageUri?.let {
            storageReference.putFile(it).addOnSuccessListener {
                hideProgressBar()
                Toast.makeText(this@AccountActivity, "Profile succesfuly updated",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                hideProgressBar()
                Toast.makeText(this@AccountActivity, "Failed to update the image",Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun getUserProfilePic(){

        //storageReference= FirebaseStorage.getInstance().getReference("Users/$uid.jpg")
        storageReference= FirebaseStorage.getInstance().getReference().child("profilPic/timon.jpg")
        val localFile = File.createTempFile("tempImage",".jpg")
        storageReference.getFile(localFile).addOnSuccessListener{
            val bitmap= BitmapFactory.decodeFile(localFile.absolutePath)
            binding.profilPic.setImageBitmap(bitmap)
            Toast.makeText( this@AccountActivity,"Picture Retrieved",Toast.LENGTH_SHORT).show()
            hideProgressBar()
        }.addOnFailureListener{
            hideProgressBar()
            Toast.makeText( this@AccountActivity,"Failed to retrieve image",Toast.LENGTH_SHORT).show()
        }

    }

    private fun capturePhoto(){
     //  val capturedImage = File(externalCacheDir, "Captured_profilPic$uid.jpg")
    //   if(capturedImage.exists()) {
      //          capturedImage.delete()
     //      }
      //  capturedImage.createNewFile()

      //  imageUri = if(Build.VERSION.SDK_INT >= 24){
      //      FileProvider.getUriForFile(this, "info.camposha.kimagepicker.fileprovider",
      //          capturedImage)
      //  } else {
      //          Uri.fromFile(capturedImage)
      //  }

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(intent, CAPTURE_PHOTO)
        }

    private fun openGallery(){

        val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, CHOOSE_PHOTO)
    }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }
    companion object {
        private val CHOOSE_PHOTO= 1000;
        private val PERMISSION_CODE = 1001;
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openGallery()
                }else{
                    Toast.makeText(this,"Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun renderImage(imagePath: String?){
        if (imagePath != null) {
            val bitmap = BitmapFactory.decodeFile(imagePath)
            binding.profilPic.setImageBitmap(bitmap)
        }
        else {
            Toast.makeText(this,"ImagePath is null",Toast.LENGTH_SHORT).show()
        }
    }
    private fun openGalleryOrTakePic() {
        val alertDialog: AlertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle("Chosissez une photo")
        alertDialog.setMessage("Veuillez prendre une photo depuis:")

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Gallery") {
                dialog, which ->
            openGallery()
            dialog.dismiss()
        }

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Camera") {
                dialog, which ->
            capturePhoto()
            dialog.dismiss()
        }
        alertDialog.show()
    }
    private fun showProgressBar(){
        dialog = Dialog(this@AccountActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        //dialog.setContentView(R.layout.wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
    private fun hideProgressBar(){
        dialog.dismiss()
    }
}



// User is signed in
//   databaseReference.child(uid).setValue(user).addOnCompleteListener {
//       if (it.isSuccessful) {
//           uploadProfile()
//     } else {
//         hideProgressBar()
//        Toast.makeText(
//            this@AccountActivity,
//            "Failed to update profile",
//             Toast.LENGTH_SHORT
//          ).show()
//      }
//   }


//send email verif
// user!!.sendEmailVerification()
//     .addOnCompleteListener { task ->
//         if (task.isSuccessful) {
//              Log.d(TAG, "Email sent.")
//         }
//      }
//update password
// user!!.updatePassword(userNewPassword)
//   .addOnCompleteListener { task ->
//       if (task.isSuccessful) {
//          Log.d(TAG, "User password updated.")
//       }
//   }

//send an email to reset password !!!!Mot de passe oublié
// Firebase.auth.sendPasswordResetEmail(email)
//     .addOnCompleteListener { task ->
//         if (task.isSuccessful) {
//            Log.d(TAG, "Email sent.")
//       }
//   }
//uploadProfile()




//  val capturedImage = File(externalCacheDir, "My_Captured_Photo.jpg")
// if(capturedImage.exists()) {
//        capturedImage.delete()
//     }
// capturedImage.createNewFile()
//  imageUri = if(Build.VERSION.SDK_INT >= 24){
//      FileProvider.getUriForFile(this, "info.camposha.kimagepicker.fileprovider",
//          capturedImage)
//  } else {
//          Uri.fromFile(capturedImage)
//  }


/*private fun uploadProfile(){

    //imageUri=Uri.parse("android.resource://$packageName/${R.drawable.profile}")
    storageReference= FirebaseStorage.getInstance().getReference("Users/"+auth.currentUser?.uid)
    imageUri=data.getData();
    storageReference.putFile(imageUri).addOnSuccessListener {
        hideProgressBar()
        Toast.makeText(this@AccountActivity, "Profile succesfuly updated",Toast.LENGTH_SHORT).show()
    }.addOnFailureListener{
        hideProgressBar()
        Toast.makeText(this@AccountActivity, "Failed to update the image",Toast.LENGTH_SHORT).show()
    }

}*/