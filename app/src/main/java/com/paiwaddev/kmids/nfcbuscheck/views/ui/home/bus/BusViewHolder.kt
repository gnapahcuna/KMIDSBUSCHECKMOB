package com.paiwaddev.kmids.kmidsmobile.view.holder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.paiwaddev.kmids.nfcbuscheck.R

class BusViewHolder(view: View): RecyclerView.ViewHolder(view){

    val tvBusName: TextView = view.findViewById(R.id.tv_bus_name)

}