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
import fr.isen.corre.livraisou.databinding.ActivityRegisterBinding


class RegisterActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    val TAG = "LoginActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        listenClick()

    }


    private fun reload() {
        TODO("Not yet implemented")
    }

    private fun listenClick() {
        binding.RegisterButton.setOnClickListener {
            register()
        }

        binding.button.setOnClickListener {
            changeActivityToLogin()
        }

        binding.btnWithoutAuth.setOnClickListener {
            changeActivityToMain()
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        TODO("Not yet implemented")
    }

    private fun changeActivityToMain() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun changeActivityToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun register() {
        auth.createUserWithEmailAndPassword(binding.EmailAddress.text.toString().trim(),binding.Password.text.toString().trim())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    changeActivityToMain()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Log.w(binding.EmailAddress.text.toString(), task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}