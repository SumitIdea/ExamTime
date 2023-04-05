package com.sumit.examtime.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Chronometer
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.sumit.examtime.R
import com.sumit.examtime.activity.ExamActivity.Companion.answers
import com.sumit.examtime.activity.ExamActivity.Companion.lines
import com.sumit.examtime.activity.ExamActivity.Companion.mPager
import com.sumit.examtime.activity.ExamActivity.Companion.questions
import java.util.*

class ExamFragment : Fragment(), View.OnClickListener {
    private var questionView: TextView? = null
    private val titleText: TextView? = null
    private var firstResponse: Button? = null
    private var secondResponse: Button? = null
    private var thirdResponse: Button? = null
    private var fourthResponse: Button? = null
    private val rand: Random? = null
    private val timer: Chronometer? = null
    private val countDownTimer: CountDownTimer? = null
    private var n = 0
    private var questionNumber = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_exam, container, false) as ViewGroup
        questionView = rootView.findViewById<View>(R.id.questionView) as TextView
        firstResponse = rootView.findViewById<View>(R.id.firstResponse) as Button
        secondResponse = rootView.findViewById<View>(R.id.secondResponse) as Button
        thirdResponse = rootView.findViewById<View>(R.id.thirdResponse) as Button
        fourthResponse = rootView.findViewById<View>(R.id.fourthResponse) as Button
        n = requireArguments().getInt("questionToFind")
        questionNumber = requireArguments().getInt("position")
        firstResponse!!.setOnClickListener(this)
        secondResponse!!.setOnClickListener(this)
        thirdResponse!!.setOnClickListener(this)
        fourthResponse!!.setOnClickListener(this)
        val text = (questionNumber + 1).toString() + ". " + lines!!.get(5 * n)
            .substring(3, lines!!.get(5 * n).length)
        questionView!!.text = text
        firstResponse!!.setText(lines!!.get(5 * n + 1))
        secondResponse!!.setText(lines!!.get(5 * n + 2))
        thirdResponse!!.setText(lines!!.get(5 * n + 3))
        fourthResponse!!.setText(lines!!.get(5 * n + 4))
        if (!requireArguments().getBoolean("Instant")) {
            if (questions!!.get(questionNumber).get(1).equals("A")) {
                firstResponse!!.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            } else if (questions!!.get(questionNumber).get(1).equals("B")) {
                secondResponse!!.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            } else if (questions!!.get(questionNumber).get(1).equals("C")) {
                thirdResponse!!.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            } else if (questions!!.get(questionNumber).get(1).equals("D")) {
                fourthResponse!!.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            }
        } else {
            if (!questions!!.get(questionNumber).get(1).equals("Z")) {
                setBothColours()
            }
        }
        return rootView
    }

    private fun selectedColourChange(input: Button?, `in`: String) {
        val choice: String = questions!!.get(questionNumber).get(1)
        // Changes previous selected button back to original colour
        if (choice != "Z") {
            if (choice == "A") {
                firstResponse!!.setBackgroundColor(resources.getColor(R.color.questionButtonColour))
            } else if (choice == "B") {
                secondResponse!!.setBackgroundColor(resources.getColor(R.color.questionButtonColour))
            } else if (choice == "C") {
                thirdResponse!!.setBackgroundColor(resources.getColor(R.color.questionButtonColour))
            } else if (choice == "D") {
                fourthResponse!!.setBackgroundColor(resources.getColor(R.color.questionButtonColour))
            }
        }
        //Sets the new answer as a different colour
        if (requireArguments().getBoolean("Instant", false)) {
            val answer: String = answers!!.get(questions!!.get(questionNumber).get(0).toInt())
            if (answer == `in`) {
                input!!.setBackgroundColor(resources.getColor(R.color.green))
            } else {
                input!!.setBackgroundColor(resources.getColor(R.color.red))
                if (answer == "A") {
                    firstResponse!!.setBackgroundColor(resources.getColor(R.color.green))
                } else if (answer == "B") {
                    secondResponse!!.setBackgroundColor(resources.getColor(R.color.green))
                } else if (answer == "C") {
                    thirdResponse!!.setBackgroundColor(resources.getColor(R.color.green))
                } else if (answer == "D") {
                    fourthResponse!!.setBackgroundColor(resources.getColor(R.color.green))
                }
            }
        } else {
            input!!.setBackgroundColor(resources.getColor(R.color.colorPrimary))
        }
        questions!![questionNumber][1] = `in` //Mark the answer change in the answers array
    }

    private fun delay() {
        val handler = Handler()
        val duration: Int
        duration = if (requireArguments().getBoolean("Instant", false)) {
            3000
        } else {
            1000
        }
        val currentItem: Int = mPager!!.getCurrentItem()
        handler.postDelayed({
            if (mPager!!.getCurrentItem() < 99 && currentItem == mPager!!.getCurrentItem()) {
                mPager!!.setCurrentItem(currentItem + 1)
            }
        }, duration.toLong())
    }

    fun setBothColours() {
        if (questions!!.get(questionNumber).get(1).equals("A")) {
            firstResponse!!.setBackgroundColor(resources.getColor(R.color.red))
        } else if (questions!!.get(questionNumber).get(1).equals("B")) {
            secondResponse!!.setBackgroundColor(resources.getColor(R.color.red))
        } else if (questions!!.get(questionNumber).get(1).equals("C")) {
            thirdResponse!!.setBackgroundColor(resources.getColor(R.color.red))
        } else if (questions!!.get(questionNumber).get(1).equals("D")) {
            fourthResponse!!.setBackgroundColor(resources.getColor(R.color.red))
        }
        if (answers!!.get(questions!!.get(questionNumber).get(0).toInt()).equals("A")) {
            firstResponse!!.setBackgroundColor(resources.getColor(R.color.green))
        } else if (answers!!.get(questions!!.get(questionNumber).get(0).toInt()).equals("B")) {
            secondResponse!!.setBackgroundColor(resources.getColor(R.color.green))
        } else if (answers!!.get(questions!!.get(questionNumber).get(0).toInt()).equals("C")) {
            thirdResponse!!.setBackgroundColor(resources.getColor(R.color.green))
        } else if (answers!!.get(questions!!.get(questionNumber).get(0).toInt()).equals("D")) {
            fourthResponse!!.setBackgroundColor(resources.getColor(R.color.green))
        }
        firstResponse!!.isEnabled = false
        secondResponse!!.isEnabled = false
        thirdResponse!!.isEnabled = false
        fourthResponse!!.isEnabled = false
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.firstResponse -> {
                selectedColourChange(firstResponse, "A")
                delay()
            }
            R.id.secondResponse -> {
                selectedColourChange(secondResponse, "B")
                delay()
            }
            R.id.thirdResponse -> {
                selectedColourChange(thirdResponse, "C")
                delay()
            }
            R.id.fourthResponse -> {
                selectedColourChange(fourthResponse, "D")
                delay()
            }
        }
    }
}
