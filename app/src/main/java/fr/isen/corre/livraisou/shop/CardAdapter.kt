package fr.isen.corre.livraisou.shop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.isen.corre.livraisou.databinding.CardCellBinding

class CardAdapter(
    private val shops: List<Shop>,
    private val clickListener: ShopClickListener
) : RecyclerView.Adapter<CardViewHolder>()

{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = CardCellBinding.inflate(from, parent, false)
        return CardViewHolder(binding, clickListener)
    }
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bindHook(shops[position])
    }



    override fun getItemCount(): Int = shops.size

}


