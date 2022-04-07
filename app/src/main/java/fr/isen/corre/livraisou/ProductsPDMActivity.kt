package fr.isen.corre.livraisou

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue


class ProductsPDMActivity : AppCompatActivity() {
    private lateinit var dbref : DatabaseReference
    private lateinit var productRecyclerview : RecyclerView
    private lateinit var productArrayList : ArrayList<Product>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productlist)

        productRecyclerview = findViewById(R.id.productList)
        productRecyclerview.layoutManager = LinearLayoutManager(this)
        productRecyclerview.setHasFixedSize(true)

        productArrayList = arrayListOf()
        getProductData()

    }

    private fun getProductData() {

        dbref = FirebaseDatabase.getInstance().getReference("Produits/carrefour/pdm")

        dbref.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {



                if (snapshot.exists()) {

                    for (productSnapshot in snapshot.children) {


                        val product = productSnapshot.value as? HashMap<String, String>
                        if (product != null) {
                            val prod = Product(product["nom"], product["prix"])
                            product?.let { productArrayList.add(prod) }

                        }
                    }
                    productRecyclerview.adapter = ProductAdapter(productArrayList)


                }




            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

    }
}