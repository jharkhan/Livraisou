package fr.isen.corre.livraisou

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import fr.isen.corre.livraisou.databinding.ActivityRegisterBinding
import fr.isen.corre.livraisou.databinding.FragmentShopDetailBinding
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