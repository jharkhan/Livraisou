package fr.isen.corre.livraisou

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore
import fr.isen.corre.livraisou.databinding.ActivityMapsBinding

class MapsActivity : FragmentActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityMapsBinding
    private var mMap: GoogleMap? = null
    var db: FirebaseFirestore? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // initializing our firebase firestore.
        db = FirebaseFirestore.getInstance()

        // Obtain the SupportMapFragment and get
        // notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // creating a variable for document reference.
        val documentReference = db!!.collection("MapsData").document("7QWDor9vozLaHdFYV9kh")

        // calling document reference class with on snap shot listener.
        documentReference.addSnapshotListener { value, error ->
            if (value != null && value.exists()) {
                // below line is to create a geo point and we are getting
                // geo point from firebase and setting to it.
                val geoPoint = value.getGeoPoint("casino")

                // getting latitude and longitude from geo point
                // and setting it to our location.
                val location = LatLng(
                    geoPoint!!.latitude, geoPoint.longitude
                )

                // adding marker to each location on google maps
                mMap!!.addMarker(MarkerOptions().position(location).title("Marker"))

                // below line is use to move camera.
                mMap!!.moveCamera(CameraUpdateFactory.newLatLng(location))
            } else {
                Toast.makeText(this@MapsActivity, "Error found is $error", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}