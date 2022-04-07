package fr.isen.corre.livraisou.shop

import androidx.recyclerview.widget.RecyclerView
import fr.isen.corre.livraisou.databinding.CardCellBinding

class CardViewHolder(
    private val cardCellBinding: CardCellBinding,
    private val clickListener: ShopClickListener
): RecyclerView.ViewHolder(cardCellBinding.root)

{

    fun bindHook(shop: Shop)
    {
        cardCellBinding.cover.setImageResource(shop.cover)
        cardCellBinding.title.text = shop.title
        cardCellBinding.description.text = shop.description

        cardCellBinding.cardView.setOnClickListener {
            clickListener.onClick(shop)
        }
    }
}