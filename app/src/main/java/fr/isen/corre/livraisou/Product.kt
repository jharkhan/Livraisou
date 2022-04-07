package fr.isen.corre.livraisou

data class Product(
    val type:String? = "",
    val prix:String? = "",
    val magasin:Pshops,
    val name:String? = "",
)
data class Pshops(
    val carrefour:Boolean,
    val geant:Boolean,
    val monoprix:Boolean,
)