package fr.isen.corre.livraisou

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
            R.drawable.ic_launcher_foreground,
            "Carrefour",
            "carrefour"

        )
        val auchan = Shop(
            R.drawable.ic_launcher_foreground,
            "auchan",
            "auchan"

        )
        val leclerc = Shop(
            R.drawable.ic_launcher_foreground,
            "leclerc",
            "leclerc"

        )
        val geantCasino = Shop(
            R.drawable.ic_launcher_foreground,
            "géant casino",
            "géant casino"

        )
        val shops = arrayOf(carrefour, leclerc, geantCasino, auchan)

        for (shop in shops)
        {
            shopList.add(shop)
        }

    }


    private fun getData(database: FirebaseDatabase,) {
        val shopsRef = database.getReference()
        // Read from the database
        shopsRef.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }

        })
    }

    override fun onClick(shop: Shop) {
        Log.d(TAG, " shop : $shop")
        Log.d(TAG, " shop test")
        val intent = Intent(context, ShopDetailsActivity::class.java)
        intent.putExtra(SHOP_EXTRA, shop.title)
        startActivity(intent)
    }


}