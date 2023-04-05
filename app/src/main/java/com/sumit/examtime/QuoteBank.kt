package com.sumit.examtime

import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*

/**
 * Created by Sumit Idea on 03-March-2022.
 */
class QuoteBank(private val mContext: Context) {
    fun readLine(path: String?): List<String> {
        val mLines: MutableList<String> = ArrayList()

        val am = mContext.assets
        try {
            val _is : InputStream = am.open(path!!)
            val reader = BufferedReader(InputStreamReader(_is))

            var line: String?=null
            line = reader.readLine()
            while (reader.readLine().also { line = it } != null) {
                mLines.add(line!!)
            }
//            while (line != null)
//                mLines.add(line)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return mLines
    }

    fun saveArray(array: ArrayList<String>, examName: String?) {
        var strArr = ""
        for (i in array.indices) {
            strArr += array[i] + "@"
        }
        if (strArr.length != 0) {
            strArr = strArr.substring(0, strArr.length - 1)
        }
        val e = PreferenceManager.getDefaultSharedPreferences(mContext).edit()
        e.putString(examName, strArr)
        e.commit()
    }

    fun getStringArray(examName: String?): ArrayList<String>? {
        val strArr: Array<String>
        val prefs = PreferenceManager.getDefaultSharedPreferences(mContext)
        val str = prefs.getString(examName, null)
        strArr = if (str == null || str == "") {
            return null
        } else {
            str.split("@").toTypedArray()
        }
        return ArrayList(Arrays.asList(*strArr))
    }

    val isWrongAnswersEmpty: Boolean
        get() = getStringArray("WrongAnswersQuestions") == null
    val wrongAnswersQuestions: ArrayList<String>?
        get() = getStringArray("WrongAnswersQuestions")
    val wrongAnswersAnswers: ArrayList<String>?
        get() = getStringArray("WrongAnswersAnswers")

    fun clear() {
        saveArray(ArrayList(), "WrongAnswersQuestions")
        saveArray(ArrayList(), "WrongAnswersAnswers")
    }

    fun saveWrongQuestions(
        toBeAddedQuestions: ArrayList<String>,
        lines: List<String>,
        answers: List<String>,
        toBeRemoved: ArrayList<String>
    ) {
        var a = wrongAnswersQuestions
        if (a == null) {
            a = ArrayList()
        }
        for (s in toBeAddedQuestions) {
            a.add(lines[s.toInt() * 5])
            a.add(lines[s.toInt() * 5 + 1])
            a.add(lines[s.toInt() * 5 + 2])
            a.add(lines[s.toInt() * 5 + 3])
            a.add(lines[s.toInt() * 5 + 4])
        }
        var b = wrongAnswersAnswers
        if (b == null) {
            b = ArrayList()
        }
        for (s in toBeAddedQuestions) {
            b.add(answers[s.toInt()])
        }
        if (!toBeRemoved.isEmpty()) {
            val temp = ArrayList<String>()
            var i = 0
            run {
                var x = 0
                while (x < a!!.size) {
                    if (toBeRemoved[i] != Integer.toString(x / 5)) {
                        for (y in 0..4) {
                            temp.add(a!![x + y])
                        }
                    } else {
                        if (i + 1 < toBeRemoved.size) {
                            i++
                        }
                    }
                    x += 5
                }
            }
            a = ArrayList(temp)
            temp.clear()
            i = 0
            for (x in b.indices) {
                if (toBeRemoved[i] != Integer.toString(x)) {
                    temp.add(b[x])
                } else {
                    if (i + 1 < toBeRemoved.size) {
                        i++
                    }
                }
            }
            b = ArrayList(temp)
            temp.clear()
        }
        saveArray(a, "WrongAnswersQuestions")
        saveWrongAnswers(b)
        Log.d("questions", a.toString())
        Log.d("questionsans", b.toString())
    }

    private fun saveWrongAnswers(toBeAddedAnswers: ArrayList<String>) {
        saveArray(toBeAddedAnswers, "WrongAnswersAnswers")
    }

    companion object {
        fun saveArray(ctx: Context?, array: IntArray, examName: String?) {
            var strArr = ""
            for (i in array.indices) {
                strArr += array[i].toString() + ","
            }
            if (strArr.length != 0) {
                strArr = strArr.substring(0, strArr.length - 1)
            }
            val e = PreferenceManager.getDefaultSharedPreferences(ctx).edit()
            e.putString(examName, strArr)
            e.commit()
        }

        fun getArray(ctx: Context?, examName: String?): IntArray? {
            val strArr: Array<String>
            val prefs = PreferenceManager.getDefaultSharedPreferences(ctx)
            val str = prefs.getString(examName, null)
            strArr = if (str == null || str == "") {
                return null
            } else {
                str.split(",").toTypedArray()
            }
            val array = IntArray(strArr.size)
            for (i in strArr.indices) {
                array[i] = strArr[i].toInt()
            }
            return array
        }
    }
}