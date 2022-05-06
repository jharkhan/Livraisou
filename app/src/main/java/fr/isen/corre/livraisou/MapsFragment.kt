package fr.isen.corre.livraisou

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

        val ISEN = LatLng(43.12063, 5.93966)
        googleMap.addMarker(MarkerOptions().position(ISEN).title("Marker in ISEN").icon(
            BitmapDescriptorFactory.fromResource(R.drawable.arrowten)))

        val carrefour = LatLng(43.12004, 5.93467)
        val casino = LatLng(43.13043, 5.93851)
        val monoprix = LatLng(43.1232, 5.93352)
        googleMap.addMarker(MarkerOptions().position(carrefour).title("Marker in carrefour").icon(
            BitmapDescriptorFactory.fromResource(R.drawable.arrowtwo)))
        googleMap.addMarker(MarkerOptions().position(casino).title("Marker in casino").icon(
            BitmapDescriptorFactory.fromResource(R.drawable.arrowthree)))
        googleMap.addMarker(MarkerOptions().position(monoprix).title("Marker in monoprix"))
        val zoomLevel = 16.0f

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ISEN,zoomLevel))


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}