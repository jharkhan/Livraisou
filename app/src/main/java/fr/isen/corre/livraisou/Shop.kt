package fr.isen.corre.livraisou


var shopList = mutableListOf<Shop>()
val SHOP_ID_EXTRA = "ShopIdExtra"
data class Shop(
    var cover: Int,
    var title: String,
    var description: String)