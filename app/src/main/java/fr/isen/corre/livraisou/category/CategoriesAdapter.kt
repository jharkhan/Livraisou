package fr.isen.corre.livraisou.category;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.isen.corre.livraisou.shop.CardViewHolder
import fr.isen.corre.livraisou.databinding.CardCellBinding
import fr.isen.corre.livraisou.databinding.CellCategoriesBinding

class CategoriesAdapter (
    private val categories: List<Category>,
    private val clickListener: CategoryClickListener
) : RecyclerView.Adapter<CategoryViewHolder>()

{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = CellCategoriesBinding.inflate(from, parent, false)
        return CategoryViewHolder(binding, clickListener)
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bindHook(categories[position])
    }

}
