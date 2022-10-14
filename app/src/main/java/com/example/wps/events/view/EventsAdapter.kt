package com.example.wps.events.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wps.databinding.EventLayoutBinding
import com.example.wps.repositories.room.entities.Event

class EventsAdapter : RecyclerView.Adapter<EventsAdapter.ViewHolder>() {

    private var events = ArrayList<Event>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = EventLayoutBinding.inflate(LayoutInflater.from(parent.context))

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = events[position]

        holder.title.text = event.title
        holder.description.text = event.description
    }

    override fun getItemCount(): Int {
        return events.size
    }

    fun setEvents(events: ArrayList<Event>) {
        this.events = events
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: EventLayoutBinding) : RecyclerView.ViewHolder(itemView.root) {
        val title = itemView.eventTitle
        val description = itemView.eventDescription
        val image = itemView.eventImage
        val distance = itemView.eventDistance
    }
}