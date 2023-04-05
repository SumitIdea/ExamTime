package com.sumit.examtime.activity

import android.app.DialogFragment
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.content.Intent
import android.os.CountDownTimer
import android.os.SystemClock
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.Chronometer
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.sumit.examtime.MainActivity
import com.sumit.examtime.QuoteBank
import com.sumit.examtime.R
import com.sumit.examtime.adapter.CustomAlert
import com.sumit.examtime.fragment.ExamFragment
import java.util.ArrayList
import java.util.Random


class ExamActivity : FragmentActivity(), View.OnClickListener {
    private var mQuoteBank: QuoteBank? = null
    private var titleText: TextView? = null
    private var endExam: Button? = null
    private var rand: Random? = null
    private var timer: TextView? = null
    private var countDownTimer: CountDownTimer? = null
    private var homeExam: ImageButton? = null
    private var helpButton: ImageButton? = null
    private var mPagerAdapter: PagerAdapter? = null
    private var selectedExam: String? = null
    private lateinit var visited: BooleanArray
    private var isInstantAnswer = false
    private var examType = 0
    private var n = 0
    private var num = 0
    private var correct = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exam)
        helpButton = findViewById<View>(R.id.infoButtonExam) as ImageButton
        rand = Random()
        examType = intent.getIntExtra("examType", 0)
        selectedExam = if (examType == 999) {
            "Wrong Answers Review"
        } else {
            resources.getStringArray(R.array.examChoices)[examType]
        }
        mQuoteBank = QuoteBank(this)
        //Marketing
        if (examType == 0) {
            lines = mQuoteBank!!.readLine("MarketingExamQuestions.txt")
            answers = mQuoteBank!!.readLine("MarketingExamAnswers.txt")
        } else if (examType == 1) {
            lines = mQuoteBank!!.readLine("FinanceExamQuestions.txt")
            answers = mQuoteBank!!.readLine("FinanceExamAnswers.txt")
        } else if (examType == 2) {
            lines = mQuoteBank!!.readLine("HospitalityExamQuestions.txt")
            answers = mQuoteBank!!.readLine("HospitalityExamAnswers.txt")
        } else if (examType == 3) {
            lines = mQuoteBank!!.readLine("BusinessAdminQuestions.txt")
            answers = mQuoteBank!!.readLine("BusinessAdminAnswers.txt")
        } else if (examType == 4) {
            lines = mQuoteBank!!.readLine("EntrepreneurshipExamQuestions.txt")
            answers = mQuoteBank!!.readLine("EntrepreneurshipExamAnswers.txt")
        } else {
            lines = mQuoteBank!!.wrongAnswersQuestions
            answers = mQuoteBank!!.wrongAnswersAnswers
        }
        isInstantAnswer = intent.getBooleanExtra("InstantFeedback", false)
        questions = ArrayList()
        visited = BooleanArray(lines!!.size / 5)
        num = 0
        num = if (lines!!.size / 5 < 100) {
            lines!!.size / 5
        } else {
            100
        }
        for (i in 0 until num) {
            n = rand!!.nextInt(lines!!.size / 5)
            while (visited[n]) {
                n = rand!!.nextInt(lines!!.size / 5)
            }
            visited[n] = true
            questions!!.add(arrayOf(n.toString(), "Z"))
        }
        mPager = findViewById<View>(R.id.pager) as ViewPager
        mPagerAdapter = ScreenSlidePagerAdapter(supportFragmentManager)
        mPager!!.adapter = mPagerAdapter
        titleText = findViewById<View>(R.id.titleText) as TextView
        titleText!!.text = "$selectedExam Exam"
        homeExam = findViewById<View>(R.id.homeExam) as ImageButton
        homeExam!!.setOnClickListener(this)
        endExam = findViewById<View>(R.id.endExam) as Button
        endExam!!.setOnClickListener(this)
        correct = 0
        wrong = ArrayList()
        timer = findViewById<View>(R.id.timer) as TextView
        val seventymin = (1000 * 60 * 70).toLong()
        countDownTimer = object : CountDownTimer(seventymin, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val totalTime = 60000 // in milliseconds i.e. 60 seconds
                val v = String.format("%02d", millisUntilFinished / totalTime)
                val va = (millisUntilFinished % totalTime / 1000).toInt()
                timer!!.text = v + ":" + String.format("%02d", va)
            }

            override fun onFinish() {
                finishGame()
            }
        }
        (countDownTimer as CountDownTimer).start()
    }

    private fun finishGame() {
        if (!isInstantAnswer) {
            val intent = Intent(this@ExamActivity, EndGame::class.java)
            val mQuotebank = QuoteBank(this)
            val wrongNumbers = ArrayList<String>()
            val rightAnswers = ArrayList<String>()
            for (x in 0 until num) {
                if (answers!![questions!![x][0].toInt()] == questions!![x][1]) {
                    correct += 1
                    rightAnswers.add(questions!![x][0])
                } else {
                    wrong!!.add(
                        arrayOf(
                            questions!![x][0],
                            questions!![x][1], Integer.toString(x)
                        )
                    )
                    if (questions!![x][1] != "Z") {
                        wrongNumbers.add(questions!![x][0])
                    }
                }
            }
            if (examType != 999) {
                mQuotebank.saveWrongQuestions(wrongNumbers, lines!!, answers!!, ArrayList<String>())
            } else {
                mQuotebank.saveWrongQuestions(ArrayList<String>(), lines!!, answers!!, rightAnswers)
            }
            intent.putExtra("CORRECT_ANSWERS", correct)
            intent.putExtra("ExamName", selectedExam)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this@ExamActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (mPager!!.currentItem == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed()
        } else {
            // Otherwise, select the previous step.
            mPager!!.currentItem = mPager!!.currentItem - 1
        }
        return super.onKeyDown(keyCode, event)
    }

    private inner class ScreenSlidePagerAdapter(fm: FragmentManager?) :
        FragmentStatePagerAdapter(fm!!) {
        override fun getItem(position: Int): Fragment {
            val b = Bundle()
            b.putInt("questionToFind", questions!![position][0].toInt())
            b.putInt("position", position)
            b.putBoolean("Instant", isInstantAnswer)
            val examFragment = ExamFragment()
            examFragment.setArguments(b)
            return examFragment
        }

        override fun getCount(): Int {
            return num
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.homeExam -> {
                val customAlert: DialogFragment = CustomAlert()
                customAlert.show(fragmentManager, "quit")
            }
            R.id.endExam -> finishGame()
        }
    }

    companion object {
        var answers: List<String>? = null
        var wrong: MutableList<Array<String>>? = null
        var lines: List<String>? = null
        var questions: ArrayList<Array<String>>? = null
        var mPager: ViewPager? = null
    }
}
