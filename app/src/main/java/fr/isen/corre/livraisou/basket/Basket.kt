package fr.isen.corre.livraisou.basket

import android.content.Context
import com.google.gson.GsonBuilder
import fr.isen.corre.livraisou.Product
import java.io.File
import java.io.Serializable

class Basket(val items: MutableList<BasketItem>): Serializable {

    var itemsCount: Int = 0
        get() {
            val count = items.map {
                it.quantity
            }.reduceOrNull { acc, i -> acc + i }
            return count ?: 0
        }

    var totalPrice: Float = 0F
        get() {
            return (items.map {
                it.quantity * it.product.price?.first()?.code?.toFloat()!!
            }.reduceOrNull { acc, fl -> acc + fl } ?: 0f)
        }

    fun addItem(item: Product, quantity: Int) {
        val existingItem = items.firstOrNull{ it.product.name == item.name }
        existingItem?.let {
            existingItem.quantity += quantity
        } ?: run {
            val basketItem = BasketItem(item, quantity)
            items.add(basketItem)
        }
    }

    fun removeItem(basketItem: BasketItem) {
        items.remove(basketItem)
    }

    fun clear() {
        items.removeAll { true }
    }

    fun toJson(): String {
        return GsonBuilder().create().toJson(this)
    }

    fun save(context: Context) {
        val jsonFile = File(context.cacheDir.absolutePath + BASKET_FILE)
        val json = GsonBuilder().create().toJson(this)
        jsonFile.writeText(json)
        updateCounter(context)
    }

    private fun updateCounter(context: Context) {
        val sharedPreferences =
            context.getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(ITEMS_COUNT, itemsCount)
        editor.apply()
    }

    companion object {
        fun getBasket(context: Context): Basket {
            val jsonFile = File(context.cacheDir.absolutePath + BASKET_FILE)
            if(jsonFile.exists()) {
                val json = jsonFile.readText()
                return GsonBuilder().create().fromJson(json, Basket::class.java)
            }
            else {
                return Basket(mutableListOf())
            }
        }

        //val totalPrice: Float = Basket.totalPrice
        const val BASKET_FILE = "basket.json"
        const val ITEMS_COUNT = "ITEMS_COUNT"
        const val USER_PREFERENCES_NAME = "USER_PREFERENCES_NAME"
    }
}

class BasketItem(val product: Product, var quantity: Int): Serializable { }