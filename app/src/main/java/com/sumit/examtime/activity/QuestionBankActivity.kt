package com.sumit.examtime.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ExpandableListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.sumit.examtime.QuoteBank
import com.sumit.examtime.R
import com.sumit.examtime.adapter.ExpandableListAdapter
import com.sumit.examtime.adapter.OnChildViewClickListener


class QuestionBankActivity : AppCompatActivity(), OnChildViewClickListener
{
    private var lines: List<String>? = null
    private var answers: List<String>? = null
    val mQuoteBank: QuoteBank = QuoteBank(this)
    private var listView: ExpandableListView? = null
    private var mAdapter: ExpandableListAdapter? = null
    private var toolbar: Toolbar? = null
    // creating variable for searchview
    val newAnswer = ArrayList<String>()

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_bank)
        supportActionBar?.hide();
        var exam = ""
        val i = intent.getIntExtra("QuestionSet", 0)
        exam = resources.getStringArray(R.array.examChoices)[i]
        if (i == 0) {
            lines = mQuoteBank.readLine("MarketingExamQuestions.txt")
            answers = mQuoteBank.readLine("MarketingExamAnswers.txt")
        } else if (i == 1) {
            lines = mQuoteBank.readLine("FinanceExamQuestions.txt")
            answers = mQuoteBank.readLine("FinanceExamAnswers.txt")
        } else if (i == 2) {
            lines = mQuoteBank.readLine("HospitalityExamQuestions.txt")
            answers = mQuoteBank.readLine("HospitalityExamAnswers.txt")
        } else if (i == 3) {
            lines = mQuoteBank.readLine("BusinessAdminQuestions.txt")
            answers = mQuoteBank.readLine("BusinessAdminAnswers.txt")
        } else if (i == 4) {
            lines = mQuoteBank.readLine("EntrepreneurshipExamQuestions.txt")
            answers = mQuoteBank.readLine("EntrepreneurshipExamAnswers.txt")
        }else if (i == 5) {
            lines = mQuoteBank.readLine("EntrepreneurshipExamQuestions.txt")
            answers = mQuoteBank.readLine("EntrepreneurshipExamAnswers.txt")
        }else if (i == 6) {
            lines = mQuoteBank.readLine("EntrepreneurshipExamQuestions.txt")
            answers = mQuoteBank.readLine("EntrepreneurshipExamAnswers.txt")
        }
        else if (i == 7) {
            lines = mQuoteBank.readLine("EntrepreneurshipExamQuestions.txt")
            answers = mQuoteBank.readLine("EntrepreneurshipExamAnswers.txt")
        }
        else {
            lines = mQuoteBank.wrongAnswersQuestions
            answers = mQuoteBank.wrongAnswersAnswers
        }
        answerResult()

        val q = ArrayList<String>()
        val h = HashMap<String, ArrayList<String>>()
        var j = 0
//        Log.d("QuestionBankActivity", "Lines size: ${lines!!.size}")
        while (j + 4 < lines!!.size) {
//            Log.d("QuestionBankActivity", "Processing question ${j/5}")
            q.add(lines!![j])
            val temp = ArrayList<String>()
            for (x in 1..4) {
                temp.add(lines!![j + x])
            }
            h[lines!![j]] = temp
            j += 5
        }


        if (j != lines!!.size) {
            // Handle error condition
            // throw RuntimeException("Unexpected end of file")
            // Handle error condition
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Error")
            builder.setMessage("The selected file does not contain a complete set of questions.")
            builder.setPositiveButton("OK") { dialog, which ->
                // Do something when OK button is clicked
                // For example, prompt the user to choose a different file
            }
            builder.show()
        }
//            Log.e("REsult.....", newAnswer.toString())


        mAdapter = ExpandableListAdapter(this, q, h)
        mAdapter?.setOnChildViewClickListener(this)
        listView = findViewById<View>(R.id.questionBankList) as ExpandableListView
        listView!!.setAdapter(mAdapter)
        toolbar = findViewById<View>(R.id.question_bank_toolbar) as Toolbar
        val backArrow = resources.getDrawable(R.drawable.ic_arrow_back_black_24dp)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            backArrow.setTint(resources.getColor(R.color.white))
        }
        toolbar!!.navigationIcon = backArrow
        toolbar!!.setNavigationOnClickListener { finish() }
        toolbar!!.title = "$exam Questions"
        toolbar!!.setTitleTextColor(resources.getColor(R.color.white))

    }

    fun answerResult(){
        val newHash = HashMap<String, ArrayList<String>>()
        var j = 0
//        Log.d("QuestionBankActivity", "Lines size: ${answers!!.size}")
        while (j + 4 < answers!!.size) {
//            Log.d("QuestionBankActivity", "Processing question ${j/5}")
            newAnswer.add(answers!![j])
            val temp = ArrayList<String>()
            for (x in 1..4) {
                temp.add(answers!![j + x])
            }
            newHash[answers!![j]] = temp
            j += 5
//            Log.e("REsult   NewAnswwrr.....", newAnswer[0].toString())


        }

    }

    override fun onChildViewClick(id: Int, option: TextView) {
        Log.e("DATA,,,",id.toString())
        if (id==0){
            Toast.makeText(this,"Option a", Toast.LENGTH_LONG).show()
        }
        else if (id==1)
            {
                Toast.makeText(this,"Option B", Toast.LENGTH_LONG).show()
            }
        else if (id==2)
            {
                Toast.makeText(this,"Option C", Toast.LENGTH_LONG).show()
            }
        else if (id==3)
            {
                Toast.makeText(this,"Option D", Toast.LENGTH_LONG).show()
                option.setTextColor(Color.RED)

            }
    }

//    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//        Log.e("DATA,,,",position.toString())
//        if (position==0){
//            Toast.makeText(this,"Option a", Toast.LENGTH_LONG).show()
//        }
//    }
}
