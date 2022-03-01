package fr.isen.corre.livraisou

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import fr.isen.corre.livraisou.databinding.ActivityMainBinding
import android.content.Intent
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityMainBinding
    val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn.setOnClickListener {
            changeActivityToAccount()
        }
    }



    private fun changeActivityToAccount() {
        startActivity(Intent(this, AccountActivity::class.java))
    }
}




