package fr.isen.corre.livraisou

import android.graphics.drawable.Drawable

var shopList = mutableListOf<Shop>()
val SHOP_ID = "ShopId"
data class Shop(
    var cover: Int,
    var title: String,
    var description: String,
    val id: Int? = shopList.size)