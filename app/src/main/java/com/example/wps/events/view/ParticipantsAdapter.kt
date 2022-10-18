package com.example.wps.events.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wps.databinding.ParticipantLayoutBinding

class ParticipantsAdapter : RecyclerView.Adapter<ParticipantsAdapter.ViewHolder>() {

    private var participantsNames = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ParticipantLayoutBinding.inflate(LayoutInflater.from(parent.context))

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val participant = participantsNames[position]
        holder.name.text = participant
    }

    override fun getItemCount(): Int {
        return participantsNames.size
    }

    fun setParticipants(participants: ArrayList<String>) {
        this.participantsNames = participants
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: ParticipantLayoutBinding) : RecyclerView.ViewHolder(itemView.root) {
        val name = itemView.personTextView
    }
}