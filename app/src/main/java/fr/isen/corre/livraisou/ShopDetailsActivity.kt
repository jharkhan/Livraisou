package fr.isen.corre.livraisou

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import fr.isen.corre.livraisou.databinding.ActivityDetailBinding

class ShopDetailsActivity: AppCompatActivity() {
    private lateinit var  binding: ActivityDetailBinding
    val TAG = "ShopDetailsActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val shopName = intent.getStringExtra(SHOP_ID_EXTRA)
        val shop = shopName?.let { shopFromID(it) }

        shop?.let {
            binding.cover.setImageResource(it.cover)
            binding.title.text = it.title
            binding.description.text = it.description
        }
    }

    private fun shopFromID(shopName: String): Shop?
    {
        for(shop in shopList)
        {
            if(shop.title == shopName)
                return shop
        }
        return null
    }

}