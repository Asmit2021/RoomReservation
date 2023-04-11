package com.example.ssdcproject

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase
import java.time.LocalDate
import java.util.*

class MainActivity : AppCompatActivity() {
    private var startBtn:Button?=null
    private var endBtn:Button?=null
    private var startTv:TextView?=null
    private var endTv:TextView?=null
    private var submitBtn:Button?=null
    private var pNameEdit:EditText?=null
    private var sNameEdit:EditText?=null
    private var regNoEdit:EditText?=null
    private lateinit var start:Date
    private lateinit var end:Date
    private lateinit var current:Date
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startTv=findViewById(R.id.tv_start)
        endTv=findViewById(R.id.tv_end)
        startBtn=findViewById(R.id.start_btn)
        startBtn?.setOnClickListener {
            clickDatePicker(startTv)
        }
        endBtn=findViewById(R.id.end_btn)
        endBtn?.setOnClickListener {
            clickDatePicker(endTv)
        }
        submitBtn=findViewById(R.id.submit_btn)
        submitBtn?.setOnClickListener {
            onSubmit(it)
        }
    }
    @RequiresApi(Build.VERSION_CODES.N)
    private fun clickDatePicker(tv:TextView?){
        val myCalendar= Calendar.getInstance()
        val yearr=myCalendar.get(Calendar.YEAR)
        val monthh=myCalendar.get(Calendar.MONTH)
        val day=myCalendar.get(Calendar.DAY_OF_MONTH)
        val dpd=DatePickerDialog(this,DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            val date = "$dayOfMonth/${month+1}/$year"
            if(tv==startTv)
            {
                start=Date(dayOfMonth,month+1,year)
            }
            if(tv==endTv)
            {
                end=Date(dayOfMonth,month+1,year)
            }
            tv?.text=date
        },yearr,monthh,day)
        dpd.datePicker.minDate = System.currentTimeMillis() - 84000
        dpd.show()

    }
    private fun onSubmit(btn: View)
    {
        pNameEdit=findViewById(R.id.parent_name)
        sNameEdit=findViewById(R.id.student_name)
        regNoEdit=findViewById(R.id.registration_number)
        val pName=pNameEdit?.text.toString()
        val sName=sNameEdit?.text.toString()
        val regNo=regNoEdit?.text.toString()
        val srtDate=startTv?.text.toString()
        val endDate=endTv?.text.toString()
        val info=Info(pName,sName,regNo, srtDate , endDate )
        val reff=FirebaseDatabase.getInstance().reference.child("Pending Request")
        if ((end.year>=start.year)&&(end.day>=start.day)&&(end.month>=start.month)) {
            reff.push().setValue(info)
            Snackbar.make(btn,"Submitted. You will be contacted", Snackbar.LENGTH_SHORT).show()
        }
        else{
            Snackbar.make(btn,"Enter valid Date", Snackbar.LENGTH_SHORT).show()
        }
    }


}