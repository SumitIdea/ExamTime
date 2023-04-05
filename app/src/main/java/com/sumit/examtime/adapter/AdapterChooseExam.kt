package com.sumit.examtime.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.widget.BaseAdapter
import com.sumit.examtime.adapter.AdapterChooseExam
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sumit.examtime.R
import java.util.ArrayList

class AdapterChooseExam(private val context: Context, private val data: ArrayList<String>) :
    BaseAdapter() {
    init {
        // TODO Auto-generated constructor stub
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getCount(): Int {
        // TODO Auto-generated method stub
        //return data.size()+1;
        return data.size
    }

    override fun getItem(position: Int): String {
        // TODO Auto-generated method stub
        //if (position<data.size()) {
        return data[position]
        //} //else {
        //return "add";
        //}
    }

    override fun getItemId(position: Int): Long {
        // TODO Auto-generated method stubp
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // TODO Auto-generated method stub
        var vi = convertView
        //if (position == data.size()) {
        //vi = inflater.inflate(R.layout.row_add_exam,null);
        //} else {
        vi = inflater!!.inflate(R.layout.row_choose_exam, null)
        val choice = vi.findViewById<View>(R.id.examChoice) as TextView
        choice.text = data[position]
        //}
        return vi
    }

    companion object {
        private var inflater: LayoutInflater? = null
    }
}