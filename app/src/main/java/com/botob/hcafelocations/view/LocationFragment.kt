package com.botob.hcafelocations.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.botob.hcafelocations.R
import com.botob.hcafelocations.api.models.Location
import com.botob.hcafelocations.databinding.FragmentLocationBinding
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.OverlayWithIW
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay
import org.osmdroid.views.overlay.infowindow.MarkerInfoWindow

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [LocationsFragment]
 * in two-pane mode (on larger screen devices) or self-contained
 * on handsets.
 */
class LocationFragment : Fragment() {
    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_LOCATION = "location_name"
    }

    /**
     * The placeholder content this fragment is presenting.
     */
    private lateinit var location: Location

    private lateinit var binding: FragmentLocationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_LOCATION)) {
                // Load the placeholder content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                location = it.getSerializable(ARG_LOCATION) as Location
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationBinding.inflate(inflater, container, false)

        initializeMap()
        updateContent()

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        binding.map?.onResume()
    }

    override fun onPause() {
        super.onPause()

        binding.map?.onPause()
    }

    private fun initializeMap() {
        binding.map?.let {
            it.setTileSource(TileSourceFactory.MAPNIK)
            it.setMultiTouchControls(true)
            RotationGestureOverlay(it).apply {
                isEnabled = true
                it.overlays.add(this)
            }
        }
    }

    private fun updateContent() {
        binding.toolbarLayout?.title = location.name

        binding.map?.let {
            val marker = Marker(it)
            marker.setDefaultIcon()
            marker.position = GeoPoint(location.address.latitude, location.address.longitude)
            marker.title = location.name
            marker.subDescription = location.address.formattedAddress

            it.overlays.add(marker)

            it.controller.let { controller ->
                controller.setZoom(18.0)
                controller.setCenter(marker.position)
            }
        }
    }
}