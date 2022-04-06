package fr.isen.corre.livraisou

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import fr.isen.corre.livraisou.databinding.ActivityDetailBinding

class ShopDetailsActivity: AppCompatActivity() {
    private lateinit var  binding: ActivityDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val shopID = intent.getIntExtra(SHOP_ID, -1)
    }


}