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
import com.sumit.examtime.activity.ExamActivity.Companion.wrong
import com.sumit.examtime.adapter.MyAdapter


class WrongAnswersActivity : AppCompatActivity(), View.OnClickListener {
    private var displayWrong: ListView? = null
    private var listAdapter: MyAdapter? = null
    private var backButton: ImageButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wrong_answers)
        displayWrong = findViewById<View>(R.id.displayWrongAnswers) as ListView
        backButton = findViewById<View>(R.id.backButton) as ImageButton
        backButton!!.setOnClickListener(this)
        val wrongAnswers = ArrayList<ArrayList<String>>()
        val temp = ArrayList<String>()
        for (i in wrong!!) {
            if (questions!!.get(i[2].toInt()).get(1).equals("Z")) {
                temp.add(
                    "(UNANSWERED) " + (i[2].toInt() + 1) + ". " + lines!!.get(i[0].toInt() * 5)
                        .substring(4)
                )
            } else {
                temp.add(
                    (i[2].toInt() + 1).toString() + ". " + lines!!.get(i[0].toInt() * 5).substring(4)
                )
            }
            for (j in 1..4) {
                temp.add(lines!!.get(i[0].toInt() * 5 + j))
            }
            temp.add(answers!!.get(i[0].toInt()))
            temp.add(i[1])
            wrongAnswers.add(ArrayList(temp))
            temp.clear()
        }
        listAdapter = MyAdapter(this, wrongAnswers)
        displayWrong!!.adapter = listAdapter
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.backButton -> finish()
        }
    }
}
