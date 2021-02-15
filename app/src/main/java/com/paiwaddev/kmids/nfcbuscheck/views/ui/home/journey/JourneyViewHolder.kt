package com.paiwaddev.kmids.kmidsmobile.view.holder

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.paiwaddev.kmids.nfcbuscheck.R

class JourneyViewHolder(view: View): RecyclerView.ViewHolder(view){

    val tvJourneyName: TextView = view.findViewById(R.id.tv_journey_name)
    val btnSelect: Button = view.findViewById(R.id.btn_select_journey)

}