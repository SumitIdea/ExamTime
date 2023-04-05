package com.sumit.examtime.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.sumit.examtime.R


class MyAdapter(var context: Context, var data: ArrayList<ArrayList<String>>) :
    BaseAdapter() {
    init {
        // TODO Auto-generated constructor stub
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        Log.d("TAG", data.toString())
    }

    override fun getCount(): Int {
        // TODO Auto-generated method stub
        return data.size
    }

    override fun getItem(position: Int): Any {
        // TODO Auto-generated method stub
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        // TODO Auto-generated method stub
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // TODO Auto-generated method stub
        var vi = convertView
        vi = inflater!!.inflate(R.layout.row_wrong, null)
        val ques = vi.findViewById<View>(R.id.questionw) as TextView
        ques.text = data[position][0]
        val optOne = vi.findViewById<View>(R.id.firstw) as TextView
        optOne.text = data[position][1]
        val optTwo = vi.findViewById<View>(R.id.secondw) as TextView
        optTwo.text = data[position][2]
        val optThree = vi.findViewById<View>(R.id.thirdw) as TextView
        optThree.text = data[position][3]
        val optFour = vi.findViewById<View>(R.id.fourthw) as TextView
        optFour.text = data[position][4]
        Log.d("POSITION", Integer.toString(position))
        if (data[position][6] == "A") {
            optOne.setTextColor(ContextCompat.getColor(context, R.color.red))
        } else if (data[position][6] == "B") {
            optTwo.setTextColor(ContextCompat.getColor(context, R.color.red))
        } else if (data[position][6] == "C") {
            optThree.setTextColor(ContextCompat.getColor(context, R.color.red))
        } else if (data[position][6] == "D") {
            optFour.setTextColor(ContextCompat.getColor(context, R.color.red))
        }
        if (data[position][5] == "A") {
            optOne.setTextColor(ContextCompat.getColor(context, R.color.green))
        } else if (data[position][5] == "B") {
            optTwo.setTextColor(ContextCompat.getColor(context, R.color.green))
        } else if (data[position][5] == "C") {
            Log.d("S", data[position].toString())
            optThree.setTextColor(ContextCompat.getColor(context, R.color.green))
        } else if (data[position][5] == "D") {
            optFour.setTextColor(ContextCompat.getColor(context, R.color.green))
        }
        return vi
    }

    companion object {
        private var inflater: LayoutInflater? = null
    }
}
