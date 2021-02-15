package com.paiwaddev.kmids.nfcbuscheck.views.ui.home.end

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paiwaddev.kmids.kmidsmobile.view.adapter.EndTransportAdapter
import com.paiwaddev.kmids.nfcbuscheck.R
import com.paiwaddev.kmids.nfcbuscheck.data.model.JourneyResponse


class EndTransportFragment(private val journeyResponse: JourneyResponse) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_end_transport, container, false)

        val rcv: RecyclerView = root.findViewById(R.id.rcv_student)
        rcv.layoutManager = LinearLayoutManager(root.context)
        val itemDecor = DividerItemDecoration(root.context, DividerItemDecoration.VERTICAL)
        rcv.addItemDecoration(itemDecor)

        val tvTotal: TextView = root.findViewById(R.id.tv_total_end)
        tvTotal.setText(resources.getString(R.string.text_end_total_msg)+" "+journeyResponse.Total.toString())
        val adapter= EndTransportAdapter(journeyResponse.startjourney)
        rcv.adapter = adapter

        return root
    }
}