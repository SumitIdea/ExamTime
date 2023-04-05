package com.sumit.examtime.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.sumit.examtime.R
import com.sumit.examtime.activity.QuestionBankActivity
import com.sumit.examtime.adapter.AdapterChooseExam
import java.util.*
//Resource Page
class SecondFragment : Fragment() {
    private var resources_listview: ListView? = null
    private var adapterChooseExam: AdapterChooseExam? = null
       override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_second, container, false)
           resources_listview = rootView.findViewById<View>(R.id.resources_listview) as ListView
           val choices = ArrayList(Arrays.asList(*resources.getStringArray(R.array.examChoices)))
           adapterChooseExam = AdapterChooseExam(requireContext(), choices)
           resources_listview!!.adapter = adapterChooseExam

           resources_listview!!.onItemClickListener =
               OnItemClickListener { adapterView, view, i, l ->
                   val intent = Intent(context, QuestionBankActivity::class.java)
                   intent.putExtra("QuestionSet", i)
                   startActivity(intent)
               }
           return  rootView
    }
}