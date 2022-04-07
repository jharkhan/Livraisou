package fr.isen.corre.livraisou

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import fr.isen.corre.livraisou.OrderFinalActivity
import fr.isen.corre.livraisou.R
import fr.isen.corre.livraisou.basket.Basket
import fr.isen.corre.livraisou.databinding.ActivityBasketBinding
import org.json.JSONObject
import fr.isen.corre.livraisou.basket.BasketAdapter


class BasketActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBasketBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityBasketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadList()

        binding.orderButton.setOnClickListener {
            val intent = Intent ( this, OrderFinalActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadList() {
        val basket = Basket.getBasket(this)
        val items = basket.items
        binding.basketRecycler.layoutManager = LinearLayoutManager(this)
        binding.basketRecycler.adapter = BasketAdapter(items) {
            basket.removeItem(it)
            basket.save(this)
            loadList()
        }
    }

    //private fun makeRequest() {
    //    val path = "http://test.api.catering.bluecodegames.com/user/order"
    //    val queue = Volley.newRequestQueue(this)
    //    val jsonObject = JSONObject()

    //    val basket = Basket.getBasket(this)
    //    val sharePreferences = getSharedPreferences(UserActivity.USER_PREFERENCES_NAME, Context.MODE_PRIVATE)

    //    jsonObject.put("msg",basket.toJson())
    //    jsonObject.put("id_user",sharePreferences.getInt(UserActivity.ID_USER, -1))
    //    jsonObject.put("id_shop", 1)

    //    val request = JsonObjectRequest (
    //        Request.Method.POST, path, jsonObject, {
    //            Log.d("request", it.toString(2))
    //            basket.clear()
    //            basket.save(this)
    //            finish()
    //                                               }, {
    //            Log.d("request", it.message ?: "une erreur est survenue")
    //                                               }
    //    )
    //    queue.add(request)
    //}

    companion object {
        const val REQUEST_CODE = 111
    }
}

