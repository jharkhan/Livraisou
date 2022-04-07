package fr.isen.corre.livraisou.product

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import fr.isen.corre.livraisou.Product
import fr.isen.corre.livraisou.R
import fr.isen.corre.livraisou.databinding.ActivityProductsBinding




class ProductsActivity : AppCompatActivity(){
    private lateinit var binding: ActivityProductsBinding
     override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      val database = Firebase.database("https://livraisou-default-rtdb.firebaseio.com/")
        val myRef = database.getReference("Produits")
        val TAG = "ProductActivity"
        binding = ActivityProductsBinding.inflate(layoutInflater)
         val view = binding.root
         setContentView(view)


    // getting the recyclerview by its id
    val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

    // this creates a vertical layout Manager
    recyclerview.layoutManager = LinearLayoutManager(this)

    // ArrayList of class ItemsViewModel
    val data = ArrayList<Product>()

    // This loop will create 20 Views containing
    // the image with the count of view


    // This will pass the ArrayList to our Adapter
    val adapter = ProductAdapter(data)

    // Setting the Adapter with the recyclerview
    recyclerview.adapter = adapter
    // Read from the database
    myRef.addValueEventListener(object: ValueEventListener {

        override fun onDataChange(snapshot: DataSnapshot) {
            // This method is called once with the initial value and again
            // whenever data at this location is updated.
            val value = snapshot.value
            val post = snapshot.getValue<Map<String, Product>>()

            post?.values?.first()
            Log.d(TAG, "Value is: " + value)
        }

        override fun onCancelled(error: DatabaseError) {
            Log.w(TAG, "Failed to read value.", error.toException())
        }

    })
}

}