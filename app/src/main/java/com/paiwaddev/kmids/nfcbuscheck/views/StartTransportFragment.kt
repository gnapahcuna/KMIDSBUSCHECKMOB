package com.paiwaddev.kmids.nfcbuscheck.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.paiwaddev.kmids.nfcbuscheck.R
import com.paiwaddev.kmids.nfcbuscheck.data.model.JourneyResponse
import com.paiwaddev.kmids.nfcbuscheck.views.ui.home.HomeViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel

class StartTransportFragment() : Fragment() {

    private val homeViewModel: HomeViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_start_transport, container, false)
        // Inflate the layout for this fragment

        val iv_scan_1: ImageView = root.findViewById(R.id.iv_scan_1)
        val iv_scan_2: ImageView = root.findViewById(R.id.iv_scan_2)
        val iv_scan_3: ImageView = root.findViewById(R.id.iv_scan_3)
        val iv_scan_4: ImageView = root.findViewById(R.id.iv_scan_4)

        val scan: Animation = AnimationUtils.loadAnimation(context, R.anim.anim_scan)

        iv_scan_1.startAnimation(scan)
        val scan2: Animation = AnimationUtils.loadAnimation(context, R.anim.anim_scan)

        scan2.startOffset = 1200
        iv_scan_2.startAnimation(scan2)
        val scan3: Animation = AnimationUtils.loadAnimation(context, R.anim.anim_scan)

        scan3.startOffset = 1800
        iv_scan_3.startAnimation(scan3)
        /*val scan4: Animation = AnimationUtils.loadAnimation(context, R.anim.anim_scan)

        scan4.startOffset = 2400
        iv_scan_4.startAnimation(scan4)*/

        val tvScan: TextView = root.findViewById(R.id.tv_scaned)
        homeViewModel.countScan().observe(viewLifecycleOwner,{
            tvScan.setText(it.toString()+"/30")
        })

        return root
    }
}