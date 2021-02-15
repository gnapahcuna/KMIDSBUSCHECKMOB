package com.paiwaddev.kmids.nfcbuscheck.views.ui.home.bus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paiwaddev.kmids.kmidsmobile.view.adapter.BusAdapter
import com.paiwaddev.kmids.nfcbuscheck.R
import com.paiwaddev.kmids.nfcbuscheck.data.model.Bus
import com.paiwaddev.kmids.nfcbuscheck.views.ui.home.journey.SelectJourneyFragment


class SelectBusFragment(private val mBus: List<Bus>) : Fragment(),BusAdapter.onBusListener{

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_select_bus, container, false)

        val rcvBus: RecyclerView = root.findViewById(R.id.rcv_bus_list)
        val itemDecor = DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL)
        rcvBus.layoutManager = LinearLayoutManager(requireContext())
        rcvBus.addItemDecoration(itemDecor)

        val adapter = BusAdapter(mBus)

        adapter.setListener(this)

        rcvBus.adapter = adapter

        return root
    }

    override fun onBusClicked(item: Bus) {
        val fragMan = requireActivity().supportFragmentManager
        val fragTran = fragMan.beginTransaction()
        fragTran.replace(R.id.frameLayout, SelectJourneyFragment(item))
        fragTran.commit()
    }

}