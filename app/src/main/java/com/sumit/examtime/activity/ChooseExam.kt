package com.sumit.examtime.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.CheckBox
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.sumit.examtime.R
import com.sumit.examtime.adapter.AdapterChooseExam
import java.util.*

class ChooseExam : AppCompatActivity() {
    private var adapter: AdapterChooseExam? = null
    private var examChoices: ListView? = null
    private var choices: ArrayList<String>? = null
    private var instant: CheckBox? = null
    private var performance = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_exam)
        Log.d("DSF", resources.getStringArray(R.array.examChoices).contentToString())
        choices = java.util.ArrayList(listOf(*resources.getStringArray(R.array.examChoices)))
        examChoices = findViewById<View>(R.id.listViewChoose) as ListView
        instant = findViewById<View>(R.id.instantAnswerChecked) as CheckBox

        performance = intent.getBooleanExtra("PI", false)

        adapter = AdapterChooseExam(this, choices!!)
        examChoices!!.setAdapter(adapter)

        if (performance) {
            instant!!.visibility = View.GONE
        }
        examChoices!!.onItemClickListener = object :OnItemClickListener{
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (!performance) {
                    val intent = Intent(this@ChooseExam, ExamActivity::class.java)
                    intent.putExtra("examType", position)
                    intent.putExtra("ExamName", choices!![position])
                    intent.putExtra("InstantFeedback", instant!!.isChecked)
                    startActivity(intent)
                    finish()
                } else {
                    val intent = Intent(this@ChooseExam, PerformanceIndicators::class.java)
                    intent.putExtra("examType", position)
                    intent.putExtra("ExamName", choices!![position])
                    startActivity(intent)
                }
            }

        }
    }
}