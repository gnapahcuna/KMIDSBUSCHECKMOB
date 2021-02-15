package com.paiwaddev.kmids.kmidsmobile.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.paiwaddev.kmids.kmidsmobile.view.holder.*
import com.paiwaddev.kmids.nfcbuscheck.R
import com.paiwaddev.kmids.nfcbuscheck.data.model.BusDestination

class JourneyAdapter(private val data: List<BusDestination>): RecyclerView.Adapter<JourneyViewHolder>() {

    private lateinit var listener: onJourneyListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JourneyViewHolder {

        var holder:RecyclerView.ViewHolder? = null
        val v =  LayoutInflater.from(parent.context).inflate(R.layout.custom_journey_layout, parent, false)
        holder = JourneyViewHolder(v)

        return holder
    }

    override fun onBindViewHolder(vHolder: JourneyViewHolder, position: Int) {

        vHolder.tvJourneyName.text = data.get(position).Destination

        vHolder.btnSelect.setOnClickListener {
            this.listener.onBusClicked(data.get(position))
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }

    interface onJourneyListener{
        fun onBusClicked(item: BusDestination)
    }

    fun setListener(listener: onJourneyListener){
        this.listener = listener
    }

}