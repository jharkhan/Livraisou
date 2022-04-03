package fr.isen.corre.livraisou

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import fr.isen.corre.livraisou.databinding.FragmentShopDetailBinding
import fr.isen.corre.livraisou.databinding.FragmentShopListBinding

class ShopDetailsFragment : Fragment() {
    private lateinit var  binding: FragmentShopDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentShopDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}