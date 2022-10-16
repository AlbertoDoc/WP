package com.example.wps.events.view

import android.animation.ValueAnimator
import android.app.ActionBar
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wps.R
import com.example.wps.databinding.EventLayoutBinding
import com.example.wps.repositories.room.entities.Event
import com.example.wps.util.DateUtil
import kotlin.math.ceil

class EventsAdapter : RecyclerView.Adapter<EventsAdapter.ViewHolder>() {

    private var events = ArrayList<Event>()
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = EventLayoutBinding.inflate(LayoutInflater.from(parent.context))

        context = parent.context

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = events[position]

        holder.title.text = event.title
        holder.description.text = event.description
        Glide.with(holder.itemView.context)
            .load(event.image)
            .error(R.drawable.ic_round_image_108)
            .placeholder(R.drawable.ic_round_image_108)
            .into(holder.image)
        holder.price.text = "R$ " + event.price
        holder.date.text = DateUtil.parseLongToFormattedDateString(event.date)

        holder.eventLayout.setOnClickListener { showBottomSheetDialog(event) }

        holder.seeMore.setOnClickListener {
            // Animation for See More click
            val height = holder.description.measuredHeight
            val finalHeight = ceil(height / 3.0) * holder.description.lineCount

            holder.description.layoutParams.height = ActionBar.LayoutParams.WRAP_CONTENT

            val heightAnimator = ValueAnimator.ofInt(height, finalHeight.toInt())
            heightAnimator.addUpdateListener {
                holder.description.height = (it.animatedValue as Int)
            }
            heightAnimator.start()

            holder.seeMore.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return events.size
    }

    fun setEvents(events: ArrayList<Event>) {
        this.events = events
        notifyDataSetChanged()
    }

    private fun showBottomSheetDialog(event: Event) {
        val dialog = EventBottomSheetDialog(context, R.style.BottomSheetDialog, event)
        dialog.show()
    }

    class ViewHolder(itemView: EventLayoutBinding) : RecyclerView.ViewHolder(itemView.root) {
        val title = itemView.eventTitle
        val description = itemView.eventDescription
        val seeMore = itemView.seeMore
        val image = itemView.eventImage
        val distance = itemView.eventDistance
        val price = itemView.eventPrice
        val date = itemView.eventDate
        val eventLayout = itemView.eventLayout
    }
}