package com.paiwaddev.kmids.kmidsmobile.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.paiwaddev.kmids.kmidsmobile.view.holder.EndTransportViewHolder
import com.paiwaddev.kmids.kmidsmobile.view.holder.SettingsTouchIDViewHolder
import com.paiwaddev.kmids.kmidsmobile.view.holder.SettingsViewHolder
import com.paiwaddev.kmids.nfcbuscheck.R
import com.paiwaddev.kmids.nfcbuscheck.data.model.Journey

class EndTransportAdapter(private val data: List<Journey>): RecyclerView.Adapter<EndTransportViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EndTransportViewHolder {

        var holder:RecyclerView.ViewHolder? = null
        val v =  LayoutInflater.from(parent.context).inflate(R.layout.student_custom, parent, false)
        holder = EndTransportViewHolder(v)

        return holder
    }

    override fun onBindViewHolder(vHolder: EndTransportViewHolder, position: Int) {

        vHolder.tvStudentID.text = data.get(position).Code
        vHolder.tvStudentName.text = data.get(position).Name
        vHolder.tvStudentGrade.text = data.get(position).Grade

    }

    override fun getItemCount(): Int {
        return data.size
    }

}