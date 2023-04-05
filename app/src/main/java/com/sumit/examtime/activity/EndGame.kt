package com.sumit.examtime.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.DialogInterface
import android.content.Intent
import android.database.DatabaseUtils
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
//import com.google.firebase.database.DataSnapshot
//import com.google.firebase.database.DatabaseError
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.ValueEventListener
import com.sumit.examtime.MainActivity
import com.sumit.examtime.QuoteBank
import com.sumit.examtime.R


class EndGame : AppCompatActivity() {
    private val displayWrong: ListView? = null
//    private val listAdapter: MyAdapter? = null
    private var databaseUtils: DatabaseUtils? = null
    private var titleEnd: TextView? = null
    private var homeExam2: ImageButton? = null
    private var wrongAnswersButton: TextView? = null
    private var fullExam: TextView? = null
    private var newExam: TextView? = null
    private var post_button: TextView? = null
    private var scoreText: TextView? = null
    private var correctques = 0
    private var examName: String? = null
    private var selectedExam: String? = null
    private var scores: ArrayList<Int>? = null
    private var savedScores: Boolean? = null
    private var posted: Boolean? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end_game)
        supportActionBar?.hide();
        titleEnd = findViewById<View>(R.id.titleEnd) as TextView
        examName = intent.getStringExtra("ExamName") + " Exam"
        selectedExam = intent.getStringExtra("ExamName")
        titleEnd!!.text = examName
        savedScores = false
        posted = false
        correctques = intent.getIntExtra("CORRECT_ANSWERS", 0)
        if (correctques > 100) {
            correctques = correctques / 2
            posted = true
        }
        questions = intent.getIntegerArrayListExtra("QUESTIONS")
        val display = "Score: $correctques/100"
        scoreText = findViewById<View>(R.id.scoreText) as TextView
        scoreText!!.text = display
        homeExam2 = findViewById<View>(R.id.homeExam2) as ImageButton
        homeExam2!!.setOnClickListener {
            val `in` = Intent(this@EndGame, MainActivity::class.java)
            startActivity(`in`)
            finish()
        }
        wrongAnswersButton = findViewById<View>(R.id.wrongAnswersButton) as TextView
        buttonAnimation(wrongAnswersButton)
        fullExam = findViewById<View>(R.id.fullExamButton) as TextView
        buttonAnimation(fullExam)
        newExam = findViewById<View>(R.id.newExamButton) as TextView
        buttonAnimation(newExam)
        post_button = findViewById<View>(R.id.postButton) as TextView
        buttonAnimation(post_button)
    }

    fun buttonAnimation(button: TextView?) {
        button!!.setOnTouchListener(OnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    val scaleDownX = ObjectAnimator.ofFloat(button, "scaleX", 0.9f)
                    val scaleDownY = ObjectAnimator.ofFloat(button, "scaleY", 0.9f)
                    scaleDownX.duration = 0
                    scaleDownY.duration = 0
                    val scaleDown = AnimatorSet()
                    scaleDown.play(scaleDownX).with(scaleDownY)
                    scaleDown.start()
                    return@OnTouchListener true
                }
                MotionEvent.ACTION_UP -> {
                    buttonAction(button)
                    val scaleDownX2 = ObjectAnimator.ofFloat(button, "scaleX", 1f)
                    val scaleDownY2 = ObjectAnimator.ofFloat(button, "scaleY", 1f)
                    scaleDownX2.duration = 0
                    scaleDownY2.duration = 0
                    val scaleDown2 = AnimatorSet()
                    scaleDown2.play(scaleDownX2).with(scaleDownY2)
                    scaleDown2.start()
                    return@OnTouchListener true
                }
            }
            true
        })
    }

    private fun buttonAction(button: TextView?) {
        when (button!!.id) {
            R.id.wrongAnswersButton -> {
                val intent = Intent(this@EndGame, WrongAnswersActivity::class.java)
                startActivity(intent)
            }
            R.id.fullExamButton -> {
                val intent1 = Intent(this@EndGame, FullExam::class.java)
                startActivity(intent1)
            }
            R.id.newExamButton -> {
                val `in` = Intent(this@EndGame, ExamActivity::class.java)
                startActivity(`in`)
                finish()
            }
            R.id.postButton -> requestPost()
        }
    }

    private fun requestPost() {
        val builder: AlertDialog.Builder =
            AlertDialog.Builder(this).setMessage(R.string.post_message).setPositiveButton("Post",
                DialogInterface.OnClickListener { dialogInterface, i ->
                    if (!posted!!) {
//                        saveScoresDatabase()
                        saveScoresLocal()
                    }
                    if (savedScores!!) {
                        Toast.makeText(baseContext, "Scores Already Saved", Toast.LENGTH_LONG)
                            .show()
                    }
                }).setNegativeButton("Cancel",
                DialogInterface.OnClickListener { dialogInterface, i -> })
        builder.create().show()
    }

//    private fun saveScoresDatabase() {
//        scores = ArrayList()
//        databaseUtils = DatabaseUtils()
//        val mReference: DatabaseReference =
//            databaseUtils.getDatabaseInstance().getReference().child("Users")
//                .child(databaseUtils.getUserID()).child("Scores").child(examName)
//        mReference.addListenerForSingleValueEvent(object : ValueEventListener() {
//            fun onDataChange(dataSnapshot: DataSnapshot) {
//                scores = dataSnapshot.getValue()
//                if (scores == null) {
//                    scores = ArrayList()
//                }
//                if (!savedScores!!) {
//                    scores!!.add(intent.getIntExtra("CORRECT_ANSWERS", 0))
//                    databaseUtils.getDatabaseInstance().getReference().child("Users")
//                        .child(databaseUtils.getUserID()).child("Scores").child(examName)
//                        .setValue(scores)
//                    //ExamClass exam = new ExamClass(questions);
//                    //databaseUtils.getDatabaseInstance().getReference().child("Users").child(databaseUtils.getUserID()).child("Past Exams").child();
//                    //databaseUtils.getDatabaseInstance().getReference().child("Users").child(databaseUtils.getUserID()).child("PastExams").child()
//                    savedScores = true
//                    Toast.makeText(baseContext, "Score Has Been Saved", Toast.LENGTH_LONG).show()
//                }
//            }
//
//            fun onCancelled(databaseError: DatabaseError) {}
//        })
//    }

    private fun saveScoresLocal() {
        var dataPoints: IntArray
        dataPoints = QuoteBank.getArray(this, selectedExam)!!
        if (dataPoints == null) {
            dataPoints = IntArray(1)
            dataPoints[0] = 0
        }
        val dataPoints2 = IntArray(dataPoints.size + 1)
        for (i in dataPoints.indices) {
            dataPoints2[i] = dataPoints[i]
        }
        dataPoints2[dataPoints2.size - 1] = correctques
        QuoteBank.saveArray(this, dataPoints2, selectedExam)
    }

    companion object {
        var questions: ArrayList<Int>? = null
    }
}
