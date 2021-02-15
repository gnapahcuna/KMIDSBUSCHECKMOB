package com.paiwaddev.kmids.kmidsmobile.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.paiwaddev.kmids.kmidsmobile.view.holder.BusViewHolder
import com.paiwaddev.kmids.kmidsmobile.view.holder.EndTransportViewHolder
import com.paiwaddev.kmids.kmidsmobile.view.holder.SettingsTouchIDViewHolder
import com.paiwaddev.kmids.kmidsmobile.view.holder.SettingsViewHolder
import com.paiwaddev.kmids.nfcbuscheck.R
import com.paiwaddev.kmids.nfcbuscheck.data.model.Bus

class BusAdapter(private val data: List<Bus>): RecyclerView.Adapter<BusViewHolder>() {

    private lateinit var listener: onBusListener
    private lateinit var v: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusViewHolder {

        var holder:RecyclerView.ViewHolder? = null
        v =  LayoutInflater.from(parent.context).inflate(R.layout.custom_bus_layout, parent, false)
        holder = BusViewHolder(v)

        return holder
    }

    override fun onBindViewHolder(vHolder: BusViewHolder, position: Int) {

        vHolder.tvBusName.text = data.get(position).BusName

        v.setOnClickListener {
            this.listener.onBusClicked(data.get(position))
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }

    interface onBusListener{
        fun onBusClicked(item: Bus)
    }

    fun setListener(listener: onBusListener){
        this.listener = listener
    }

}