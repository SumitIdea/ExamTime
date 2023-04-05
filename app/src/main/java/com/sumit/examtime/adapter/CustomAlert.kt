package com.sumit.examtime.adapter

import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import com.sumit.examtime.MainActivity
import com.sumit.examtime.R

/**
 * Created by Srihari Vishnu on 2018-02-18.
 */
class CustomAlert : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage(R.string.exit_home_message).setPositiveButton(
            R.string.positive_exam_message,
            DialogInterface.OnClickListener { dialog, which ->
                val intent = Intent(activity, MainActivity::class.java)
                intent.putExtra("isReturning", true)
                startActivity(intent)
                activity.finish()
            }).setNegativeButton(
            R.string.negative_exam_message,
            DialogInterface.OnClickListener { dialog, which -> })
        return builder.create()
    }
}