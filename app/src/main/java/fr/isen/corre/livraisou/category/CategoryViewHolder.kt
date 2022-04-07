package fr.isen.corre.livraisou.category;

import androidx.recyclerview.widget.RecyclerView
import fr.isen.corre.livraisou.databinding.CellCategoriesBinding

class CategoryViewHolder (
    private val cellCategoriesBinding: CellCategoriesBinding,
    private val clickListener: CategoryClickListener
): RecyclerView.ViewHolder(cellCategoriesBinding.root)

{
    fun bindHook(category: Category)
    {
        cellCategoriesBinding.nameCategory.text = category.title

        cellCategoriesBinding.spinner.setOnClickListener {
            clickListener.onClick(category)
        }
    }
}