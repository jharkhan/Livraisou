package fr.isen.corre.livraisou.shop

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import fr.isen.corre.livraisou.R
import fr.isen.corre.livraisou.databinding.FragmentShopListBinding

class ShopListFragment : Fragment(), ShopClickListener {
    private  lateinit var binding: FragmentShopListBinding
    val TAG = "ShopListFragment"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentShopListBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Firebase.database
        if(shopList.isEmpty())
            fillList()
        val activity = this
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 1)
            adapter = CardAdapter(shopList, activity)

            this.setOnClickListener {
                showDetails()
            }
        }
    }

    private fun showDetails() {
        val intent = Intent(context, ShopDetailsActivity::class.java)
        startActivity(intent)
    }


    private fun fillList() {


        val carrefour = Shop(
            R.drawable.carrefou_logo,
            "Carrefour",
            "carrefour"

        )
        val casino = Shop(
            R.drawable.casino_logo,
            "Casino",
            "casino"

        )
        val monoprix = Shop(
            R.drawable.monoprix,
            "Leclerc",
            "leclerc"

        )
        val shops = arrayOf(carrefour, casino, monoprix)

        for (shop in shops)
        {
            shopList.add(shop)
        }

    }

    override fun onClick(shop: Shop) {
        Log.d(TAG, " shop : $shop")
        Log.d(TAG, " shop test")
        val intent = Intent(context, ShopDetailsActivity::class.java)
        intent.putExtra(SHOP_EXTRA, shop.title)
        startActivity(intent)
    }


}