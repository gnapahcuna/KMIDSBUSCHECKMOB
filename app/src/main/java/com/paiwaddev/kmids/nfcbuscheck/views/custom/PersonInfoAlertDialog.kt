package com.paiwaddev.kmids.nfcbuscheck.views.custom

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.paiwaddev.kmids.nfcbuscheck.R
import com.paiwaddev.kmids.nfcbuscheck.data.model.PersonInfo
import com.paiwaddev.kmids.nfcbuscheck.views.ui.home.HomeViewModel
import com.paiwaddev.paiwadpos.utils.module.STREAM_URL
import org.koin.android.viewmodel.ext.android.sharedViewModel

class PersonInfoAlertDialog(personInfo: PersonInfo) : DialogFragment() {

    private val homeViewModel: HomeViewModel by sharedViewModel()
    private val mPersonInfo = personInfo

    companion object {

        const val TAG = "PersonInfoAlertDialog"

        fun newInstance(personInfo: PersonInfo): PersonInfoAlertDialog {
            val fragment = PersonInfoAlertDialog(personInfo)
            return fragment
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.custom_personinfo_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCancelable(false)

        val imgPerson = dialog?.findViewById<ImageView>(R.id.img_person)
        val tvCode = dialog?.findViewById<TextView>(R.id.tv_person_code)
        val tvName = dialog?.findViewById<TextView>(R.id.tv_person_name)
        val tvGrade = dialog?.findViewById<TextView>(R.id.tv_person_grad)

        if(mPersonInfo.Picture!=null){
            val path = mPersonInfo.Picture.replace('\\', '/')
            println(STREAM_URL + path)
            imgPerson?.let {
                Glide.with(this)
                    .load(STREAM_URL + path)
                    .circleCrop()
                    .into(it)
            }
        }
        tvCode!!.setText(mPersonInfo.Code)
        tvName!!.setText(mPersonInfo.Name)
        tvGrade!!.setText(mPersonInfo.Grade)

        Handler().postDelayed(Runnable {
            homeViewModel.CountingScan()
            dialog?.dismiss()
        }, 2000)
    }
}