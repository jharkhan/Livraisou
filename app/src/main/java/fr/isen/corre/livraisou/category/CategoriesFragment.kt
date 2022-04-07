package fr.isen.corre.livraisou.category

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import fr.isen.corre.livraisou.R
import fr.isen.corre.livraisou.product.ProductsActivity
import fr.isen.corre.livraisou.databinding.FragmentCategoriesBinding
import fr.isen.corre.livraisou.shop.CardAdapter
import fr.isen.corre.livraisou.shop.Shop
import fr.isen.corre.livraisou.shop.ShopDetailsActivity
import fr.isen.corre.livraisou.shop.shopList

class CategoriesFragment : Fragment(), CategoryClickListener {
    private lateinit var binding: FragmentCategoriesBinding
    val TAG = "CategoriesFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Firebase.database
        fillList()
        val activity = this
        binding.listCategories.apply {
            layoutManager = GridLayoutManager(context, 1)
            adapter = CategoriesAdapter(categoryList, activity)

            this.setOnClickListener {
                showProducts()
            }
        }
    }

    private fun showProducts() {
        val intent = Intent(context, ProductsActivity::class.java)
        startActivity(intent)
    }

    private fun fillList() {
        val fruitsVegetables = Category(
            "Fruits & Vegetables"
        )

        val meat = Category(
            "Meat"
        )

        val hygiene = Category(
            "Hygiène"
        )

        val categories = arrayOf(fruitsVegetables, meat, hygiene)

        for (category in categories) {
            categoryList.add(category)
        }
    }

    companion object {
    }

    override fun onClick(category: Category) {
        Log.d(TAG, " category : $category")
        Log.d(TAG, " category test")
        val intent = Intent(context, ProductsActivity::class.java)
        intent.putExtra(CATEGORY_EXTRA, category.title)
        startActivity(intent)
    }
}