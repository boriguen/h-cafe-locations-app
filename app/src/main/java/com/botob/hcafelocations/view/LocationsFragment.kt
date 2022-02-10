package com.botob.hcafelocations.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.botob.hcafelocations.R
import com.botob.hcafelocations.api.models.Location
import com.botob.hcafelocations.databinding.FragmentLocationsBinding
import com.botob.hcafelocations.databinding.LocationContentBinding
import com.botob.hcafelocations.presenter.LocationsViewModel
import kotlinx.coroutines.launch

/**
 * A Fragment representing a list of Pings. This fragment
 * has different presentations for handset and larger screen devices. On
 * handsets, the fragment presents a list of items, which when touched,
 * lead to a {@link ItemDetailFragment} representing
 * item details. On larger screens, the Navigation controller presents the list of items and
 * item details side-by-side using two vertical panes.
 */

class LocationsFragment : Fragment() {
    companion object {
        /**
         * The ID of the restaurant of fetch.
         * TODO: replace this static behavior with something more dynamic at the UI level.
         */
        private const val RESTAURANT_ID = 1
    }

    private lateinit var binding: FragmentLocationsBinding

    private lateinit var viewModel: LocationsViewModel

    /** Click Listener to trigger navigation based on if you have
     * a single pane layout or two pane layout
     */
    private val onClickListener = View.OnClickListener { locationView ->
        val location = locationView.tag as Location
        val bundle = Bundle()
        bundle.putSerializable(
            LocationFragment.ARG_LOCATION,
            location
        )

        // Leaving this not using view binding as it relies on if the view is visible the current
        // layout configuration (layout, layout-sw600dp)
        val locationFragmentContainer: View? =
            binding.root.findViewById(R.id.location_nav_container)

        if (locationFragmentContainer != null) {
            locationFragmentContainer.findNavController()
                .navigate(R.id.fragment_location, bundle)
        } else {
            locationView.findNavController().navigate(R.id.show_location, bundle)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
            .create(LocationsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeLocationsUpdate()
        triggerLocationsUpdate()
    }

    private fun observeLocationsUpdate() {
        viewModel.locations.observe(viewLifecycleOwner, { locations ->
            binding.locationList.adapter = LocationRecyclerViewAdapter(locations, onClickListener)
        })
    }

    private fun triggerLocationsUpdate() {
        binding.progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            viewModel.updateLocations(RESTAURANT_ID)
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    class LocationRecyclerViewAdapter(
        private val locations: List<Location>,
        private val onClickListener: View.OnClickListener
    ) :
        RecyclerView.Adapter<LocationRecyclerViewAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding =
                LocationContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val location = locations[position]
            holder.nameView.text = location.name

            with(holder.itemView) {
                tag = location
                setOnClickListener(onClickListener)
            }
        }

        override fun getItemCount() = locations.size

        inner class ViewHolder(binding: LocationContentBinding) :
            RecyclerView.ViewHolder(binding.root) {
            val nameView: TextView = binding.nameText
        }

    }
}