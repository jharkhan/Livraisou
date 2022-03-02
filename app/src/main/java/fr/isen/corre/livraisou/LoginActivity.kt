package fr.isen.corre.livraisou

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import fr.isen.corre.livraisou.databinding.ActivityLoginBinding



class LoginActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    val TAG = "RegisterActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        listenClick()
    }


    private fun reload() {
        TODO("Not yet implemented")
    }

    private fun listenClick() {
        binding.LoginButton.setOnClickListener {
            signIn()
        }

        binding.button.setOnClickListener {
           // changeActivity()
        }

        binding.buttonM.setOnClickListener {
            //changeActivity(MainActivity)
        }
    }

    private fun changeActivity(activity:Intent) {
        startActivity(Intent(this, activity::class.java))
    }

    private fun changeActivityToRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    private fun changeActivityToM() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun updateUI(user: FirebaseUser?) {
        TODO("Not yet implemented")
    }

    private fun signIn() {
        binding.LoginButton.setOnClickListener {
            auth.signInWithEmailAndPassword(
                binding.EmailAddress.text.toString(),
                binding.Password.text.toString()
            )
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        //changeActivityToMaps()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }
        }
    }

}