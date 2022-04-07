package fr.isen.corre.livraisou.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import fr.isen.corre.livraisou.Product
import fr.isen.corre.livraisou.R
import com.google.firebase.database.DatabaseReference

class ProductAdapter(private val mList: List<Product>) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentitem = mList[position]
        holder.nom.text = currentitem.name
        holder.prix.text = currentitem.price
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val nom : TextView = itemView.findViewById(R.id.tvNom)
        val prix : TextView = itemView.findViewById(R.id.tvPrix)
    }
}