package fr.isen.corre.livraisou


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.corre.livraisou.databinding.ActivityAccountBinding

class AccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAccountBinding
    val TAG = "AccountActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}





