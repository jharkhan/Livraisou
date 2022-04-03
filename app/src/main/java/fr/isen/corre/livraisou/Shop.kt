package fr.isen.corre.livraisou

var shopList = mutableListOf<Shop>()

class Shop (
    var cover: Int,
    var title: String,
    var description: String,
    val id: Int? = shopList.size)