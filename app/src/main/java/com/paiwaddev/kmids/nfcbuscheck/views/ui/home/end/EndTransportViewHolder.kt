package com.paiwaddev.kmids.kmidsmobile.view.holder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.paiwaddev.kmids.nfcbuscheck.R

class EndTransportViewHolder(view: View): RecyclerView.ViewHolder(view){

    val tvStudentID: TextView = view.findViewById(R.id.tv_student_id)
    val tvStudentName: TextView = view.findViewById(R.id.tv_student_name)
    val tvStudentGrade: TextView = view.findViewById(R.id.tv_grade)

}