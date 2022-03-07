package fr.isen.corre.livraisou

import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
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
    private var currentLocation: Location? = null
    lateinit var locationManager: LocationManager


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
                val geoPoint1 = value.getGeoPoint("casino")
                val geoPoint2 = value.getGeoPoint("carrefour")
                val geoPoint3 = value.getGeoPoint("monoprix")

                // getting latitude and longitude from geo point
                // and setting it to our location.
                val location1 = LatLng(
                    geoPoint1!!.latitude, geoPoint1.longitude
                )
                val location2 = LatLng(
                    geoPoint2!!.latitude, geoPoint2.longitude
                )
                val location3 = LatLng(
                    geoPoint3!!.latitude, geoPoint3.longitude
                )

                // adding marker to each location on google maps
                mMap!!.addMarker(MarkerOptions().position(location1).title("Casino"))
                val zoomLevel = 16.0f //This goes up to 21
                // below line is use to move camera.
                mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(location1,zoomLevel))



                mMap!!.addMarker(MarkerOptions().position(location2).title("Carrefour"))
                mMap!!.addMarker(MarkerOptions().position(location3).title("Monoprix"))
            } else {
                Toast.makeText(this@MapsActivity, "Error found is $error", Toast.LENGTH_SHORT)
                    .show()
            }

        }
        // adding on click listener to marker of google maps.
        mMap!!.setOnMarkerClickListener { marker -> // on marker click we are getting the title of our marker
            // which is clicked and displaying it in a toast message.
            val markerName = marker.title
            Toast.makeText(this@MapsActivity, "Clicked location is $markerName", Toast.LENGTH_SHORT)
                .show()
            false
        }

    }


}