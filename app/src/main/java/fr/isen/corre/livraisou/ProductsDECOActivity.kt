package fr.isen.corre.livraisou

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import fr.isen.corre.livraisou.product.ProductAdapter



class ProductsDECOActivity : AppCompatActivity() {
    private lateinit var dbref: DatabaseReference
    private lateinit var productRecyclerview: RecyclerView
    private lateinit var productArrayList: ArrayList<Product>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productlist)


        productRecyclerview = findViewById(R.id.productList)
        productRecyclerview.layoutManager = LinearLayoutManager(this)
        productRecyclerview.setHasFixedSize(true)

        productArrayList = arrayListOf<Product>()
        getproductData()
        productRecyclerview.setAdapter(ProductAdapter(productArrayList))
    }
    private fun getproductData() {

        dbref = FirebaseDatabase.getInstance().getReference("produits/carrefour/deco")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {

                    for (productSnapshot in snapshot.children) {


                        val product = productSnapshot.getValue(Product::class.java)
                        productArrayList.add(product!!)

                    }




                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

    }
}