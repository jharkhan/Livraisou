package fr.isen.corre.livraisou

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import fr.isen.corre.livraisou.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    val tag = "LoginActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        val user = Firebase.auth.currentUser
        if (user != null)
            changeActivityToMain()
        listenClick()
    }

    private fun listenClick() {
        binding.buttonLogin.setOnClickListener {
            signIn()
        }

        binding.redirectRegister.setOnClickListener {
            changeActivityToRegister()
        }
    }

    private fun changeActivityToMain() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun changeActivityToRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }



    private fun signIn() {
        val mail = binding.userMail.text.toString()
        val password = binding.userPassword.text.toString()
        if (mail.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(
                mail, password

            )
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI

                        Log.d(tag, "signInWithEmail:success")

                        changeActivityToMain()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(tag, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }
        }
        else
            Toast.makeText(baseContext, "Veuillez saisir votre adresse mail et votre mot de passe", Toast.LENGTH_SHORT).show()
    }


}