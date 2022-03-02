package fr.isen.corre.livraisou

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.corre.livraisou.databinding.ActivityMainBinding
import android.content.Intent


class MainActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityMainBinding
    val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }



    private fun changeActivityToAccount() {
        startActivity(Intent(this, AccountActivity::class.java))
    }
}




