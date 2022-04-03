package fr.isen.corre.livraisou

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import fr.isen.corre.livraisou.databinding.FragmentShopListBinding

class ShopListFragment : Fragment() {
    private  lateinit var binding: FragmentShopListBinding
    val TAG = "ShopListFragment"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentShopListBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val database = Firebase.database
        val mainActivity = this
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 1)
           // adapter = CardAdapter(bookList, mainActivity)
        }
    }

    private fun setUserInformation(dataSnapshot: DataSnapshot, user: FirebaseUser) {
        val firstName = dataSnapshot.child("surname").value
        val lastName = dataSnapshot.child("name").value
        val phoneNumber = dataSnapshot.child("phoneNum").value
    }

    private fun getData(database: FirebaseDatabase,) {
        val ShopsRef = database.getReference()
        // Read from the database
        ShopsRef.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }

        })
    }

}