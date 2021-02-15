package com.paiwaddev.kmids.nfcbuscheck.views.ui.home

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.paiwaddev.kmids.nfcbuscheck.R
import com.paiwaddev.kmids.nfcbuscheck.data.model.JourneyResponse
import com.paiwaddev.kmids.nfcbuscheck.views.custom.ProgressBuilder
import com.paiwaddev.kmids.nfcbuscheck.views.ui.home.bus.SelectBusFragment
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by sharedViewModel()
    private lateinit var dialog: ProgressBuilder

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val sharepref = requireContext().getSharedPreferences("USER_BUSCHECK", Context.MODE_PRIVATE)
        val driverName = sharepref.getString("Name",null)
        driverName?.let {
            val tvDriverName: TextView = root.findViewById(R.id.tv_driver_name)
            tvDriverName.setText(driverName)
        }

        homeViewModel.getBus().observe(viewLifecycleOwner,{ bus ->
            val fragMan = requireActivity().supportFragmentManager
            val fragTran = fragMan.beginTransaction()
            fragTran.add(R.id.frameLayout, SelectBusFragment(bus))
            fragTran.commit()
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

}
