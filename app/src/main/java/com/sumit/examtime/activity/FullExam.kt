package com.sumit.examtime.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.sumit.examtime.R
import com.sumit.examtime.activity.ExamActivity.Companion.answers
import com.sumit.examtime.activity.ExamActivity.Companion.lines
import com.sumit.examtime.activity.ExamActivity.Companion.questions
import com.sumit.examtime.adapter.MyAdapter


class FullExam : AppCompatActivity() {
    private var back: ImageButton? = null
    private var displayFullExam: ListView? = null
    private var listAdapter: MyAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_exam)
        supportActionBar?.hide();
        back = findViewById<View>(R.id.backButtonFull) as ImageButton
        displayFullExam = findViewById<View>(R.id.displayFullExam) as ListView
        back!!.setOnClickListener { finish() }
        val allQuestions = ArrayList<ArrayList<String>>()
        val temp = ArrayList<String>()
        for (i in 0 until questions!!.size) {
            if (questions!!.get(i).get(1).equals("Z")) {
                temp.add(
                    "(UNANSWERED) " + Integer.toString(i + 1) + ". " + lines!!.get(
                        questions!!.get(
                            i
                        ).get(0).toInt() * 5
                    ).substring(4)
                )
            } else {
                temp.add(
                    Integer.toString(i + 1) + ". " + lines!!.get(
                        questions!!.get(i).get(0).toInt() * 5
                    ).substring(4)
                )
            }
            for (j in 1..4) {
                temp.add(lines!!.get(questions!!.get(i).get(0).toInt() * 5 + j))
            }
            temp.add(answers!!.get(questions!!.get(i).get(0).toInt()))
            temp.add(questions!!.get(i).get(1))
            allQuestions.add(ArrayList(temp))
            temp.clear()
        }
        listAdapter = MyAdapter(this, allQuestions)
        displayFullExam!!.adapter = listAdapter
    }
}
