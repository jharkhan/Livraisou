package fr.isen.corre.livraisou

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import fr.isen.corre.livraisou.databinding.ActivityRegisterBinding


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private val tag = "LoginActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        listenClick()
    }

    private fun listenClick() {
        binding.buttonRegister.setOnClickListener {
            register()
        }

        binding.redirectLogin.setOnClickListener {
            changeActivityToLogin()
        }

    }

    private fun changeActivityToMain() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun changeActivityToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun register() {
        if (checkForm()) {
            auth.createUserWithEmailAndPassword(
                binding.userEmailAddress.text.toString().trim(),
                binding.userPassword.text.toString().trim()
            )
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(tag, "createUserWithEmail:success")
                        val database = Firebase.database
                        val user = auth.currentUser
                        user?.let {
                            val uid = it.uid
                            val userRef = database.getReference(uid)
                            userRef.setValue(
                                User(
                                    binding.userFirstName.text.toString(),
                                    binding.lastName.text.toString(),
                                    binding.userPhoneNumber.text.toString()
                                )
                            )
                        }
                        changeActivityToMain()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(tag, "createUserWithEmail:failure", task.exception)
                        Log.w(binding.userEmailAddress.text.toString(), task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }


    private fun checkForm(): Boolean {
        if ((binding.lastName.text.toString().isEmpty() || binding.userFirstName.text.toString()
                .isEmpty() || binding.userPhoneNumber.text.toString()
                .isEmpty() || binding.userEmailAddress.text.toString()
                .isEmpty() || binding.userPassword.text.toString().isEmpty())
        ) {
            Toast.makeText(baseContext, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT)
                .show()
            return false
        } else if (binding.userPassword.text!!.length < 6) {
            Toast.makeText(
                baseContext,
                "Votre mot de passe doit contenir au moins 6 caractères",
                Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (binding.userPassword.text.toString() != binding.userCheckPassword.text.toString()) {
            Toast.makeText(
                baseContext,
                "Vos mots de passe doivent etre indentiques",
                Toast.LENGTH_SHORT
            ).show()
            return false
        } else {
            return true
        }
    }
}