package fr.isen.corre.livraisou

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import fr.isen.corre.livraisou.databinding.ActivityProfileBinding
import fr.isen.corre.livraisou.databinding.ActivityShopListBinding


class ShopListActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityShopListBinding
    val TAG = "ShopListActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val database = Firebase.database
        fillList()
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 1)
            adapter = CardAdapter(shopList, this)
            val currentShop = shopList
            this.setOnClickListener {
                showDetails()
            }
        }
        supportActionBar?.hide()
        binding.goToMap.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showDetails() {
        val intent = Intent(this, ShopDetailsActivity::class.java)
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
        val ShopsRef = database.getReference()
        // Read from the database
        ShopsRef.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }

        })
    }
}