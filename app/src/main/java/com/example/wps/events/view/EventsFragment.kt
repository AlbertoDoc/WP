package com.example.wps.events.view

import android.location.LocationManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.wps.databinding.FragmentEventsBinding
import com.example.wps.events.viewModel.EventsViewModel
import com.example.wps.repositories.room.database.WPSDatabase

class EventsFragment : Fragment() {

    private var _binding: FragmentEventsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EventsViewModel by activityViewModels()
    private lateinit var locationManager : LocationManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEventsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let { WPSDatabase().getDatabase(it) }?.let { viewModel.loadDatabase(it) }
        viewModel.loadRepository()

        val adapter = EventsAdapter()
        binding.eventRecyclerView.adapter = adapter
        binding.eventRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))

        viewModel.getEvents().observe(viewLifecycleOwner) { events ->
            run {
                adapter.setEvents(ArrayList(events))
            }
        }

       //locationManager.requestLocationUpdates(
         //  LocationManager.GPS_PROVIDER,  60000L, 0, LocationListener {

          // }
       //)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}