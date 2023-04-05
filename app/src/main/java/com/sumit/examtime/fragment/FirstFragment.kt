package com.sumit.examtime.fragment

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.sumit.examtime.R
import com.sumit.examtime.activity.ChooseExam
//Home Page
class FirstFragment : Fragment() {
    private var startExam: TextView? = null
    private var wrongExam: TextView? = null
    private var piButton: TextView? = null
    private var mainLogo: ImageView? = null
    private var settingsButton: ImageButton? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_first, container, false)
        startExam = view.findViewById<TextView>(R.id.startExam) as TextView
        wrongExam = view.findViewById<View>(R.id.wrong_exam) as TextView
        piButton = view.findViewById<View>(R.id.piButton) as TextView
        mainLogo = view.findViewById<View>(R.id.logoView) as ImageView
        settingsButton = view.findViewById<View>(R.id.settingsButton) as ImageButton

        buttonAnimation(startExam!!)
        buttonAnimation(wrongExam!!)
        buttonAnimation(piButton!!)
//        buttonAnimation(settingsButton)

        return view
    }

    @SuppressLint("ClickableViewAccessibility")
    fun buttonAnimation(button: TextView) {
        button.setOnTouchListener(OnTouchListener { v, event ->
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


    private fun buttonAction(button: TextView) {
        when (button.id) {
            R.id.startExam -> {
                val intent = Intent(activity, ChooseExam::class.java)
                intent.putExtra("PI", false)
                startActivity(intent)
                requireActivity().finish()
            }
            R.id.wrong_exam -> {
//                val mQuotebank = QuoteBank(context)
//                if (!mQuotebank.isWrongAnswersEmpty()) {
//                    val `in` = Intent(activity, ExamActivity::class.java)
//                    `in`.putExtra("examType", 999)
//                    `in`.putExtra("ExamName", "Wrong Answers Review")
//                    startActivity(`in`)
//                    requireActivity().finish()
            }
//            else {
//                    val builder = AlertDialog.Builder(
//                        requireActivity()
//                    )
//                    builder.setMessage("No more Wrong Answers to Review!").setPositiveButton(
//                        "Ok"
//                    ) { dialog, which -> }
//                    builder.create().show()
//                }
//            }
            R.id.settingsButton -> {
                Toast.makeText(requireContext(), "Settings", Toast.LENGTH_LONG).show()

            }
            R.id.piButton -> {
//                val `in` = Intent(activity, ChooseExam::class.java)
//                `in`.putExtra("PI", true)
//                startActivity(`in`)
//                activity!!.finish()
            }
        }
    }
}