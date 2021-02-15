package com.paiwaddev.kmids.nfcbuscheck.views.ui.home.journey

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paiwaddev.kmids.kmidsmobile.view.adapter.JourneyAdapter
import com.paiwaddev.kmids.nfcbuscheck.NFCReaderActivity
import com.paiwaddev.kmids.nfcbuscheck.R
import com.paiwaddev.kmids.nfcbuscheck.data.model.Bus
import com.paiwaddev.kmids.nfcbuscheck.data.model.BusDestination
import com.paiwaddev.kmids.nfcbuscheck.views.custom.ProgressBuilder
import com.paiwaddev.kmids.nfcbuscheck.views.ui.WelcomeActivity
import com.paiwaddev.kmids.nfcbuscheck.views.ui.home.HomeViewModel
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class SelectJourneyFragment(private val mBus: Bus) : Fragment(),JourneyAdapter.onJourneyListener {

    private val homeViewModel: HomeViewModel by sharedViewModel()
    private lateinit var dialog: ProgressBuilder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_select_journey, container, false)

        val rcvJourney: RecyclerView = root.findViewById(R.id.rcv_journey_list)

        homeViewModel.getBusDestination(mBus.BusID).observe(viewLifecycleOwner,{destinations ->
            println(destinations.size)
            rcvJourney.layoutManager = LinearLayoutManager(requireContext())
            val adapter = JourneyAdapter(destinations)
            adapter.setListener(this)
            rcvJourney.adapter = adapter
        })

        homeViewModel.errorMessage().observe(requireActivity(), {
            if(it!=null){
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        })

        dialog = get { parametersOf(requireActivity()) }
        homeViewModel.isLoading().observe(viewLifecycleOwner, {
            if (it) {
                try {
                    dialog.showProgressDialog()
                } catch (e: Exception) {
                    println("e :" + e.message)
                }
            } else {
                dialog.dismissProgressDialog()
            }
        })

        return root
    }


    override fun onBusClicked(item: BusDestination) {
        val intent = Intent(requireActivity(), NFCReaderActivity::class.java)
        intent.putExtra("BusDestinationID",item.BusDestinationID)
        startActivity(intent)
        requireActivity().overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
        requireActivity().finish()
    }

}