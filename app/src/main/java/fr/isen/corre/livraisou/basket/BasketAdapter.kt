package fr.isen.corre.livraisou.basket

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.isen.corre.livraisou.databinding.CellBasketBinding

class BasketAdapter(private val items: List<BasketItem>,
                    val deleteClickListener: (BasketItem) -> Unit): RecyclerView.Adapter<BasketAdapter.BasketViewHolder>() {
    lateinit var context: Context
    class BasketViewHolder(binding: CellBasketBinding): RecyclerView.ViewHolder(binding.root) {
        val name: TextView = binding.cellBasketName
        val price: TextView = binding.cellBasketPrice
        val quantity: TextView = binding.quantity
        val delete: ImageButton = binding.deleteButton
        val imageView: ImageView = binding.cellImageBasket
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        context = parent.context
        return BasketViewHolder(CellBasketBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        val basketItem = items[position]
        holder.name.text = basketItem.product.name
        holder.quantity.text = "Quantity: ${basketItem.quantity.toString()}"
        holder.price.text = "${basketItem.product.price?.first()} €"
        holder.delete.setOnClickListener {
            deleteClickListener.invoke(basketItem)
        }
    }

    override fun getItemCount(): Int {
        return items.count()
    }
}