package com.sumit.examtime.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.sumit.examtime.R


open class ExpandableListAdapter(
    private var context: Context,
    private var questions: List<String>,
    private var options: HashMap<String, ArrayList<String>>,

) : BaseExpandableListAdapter() {
    private var mListener: OnChildViewClickListener? = null

    open fun setOnChildViewClickListener(listener: OnChildViewClickListener?) {
        mListener = listener
    }
//    open fun ExpandableListAdapter(context: Context,questions: List<String>,options: HashMap<String, ArrayList<String>>,newAnswer: ArrayList<String>) {
//        this.context=context;
//        this.questions=questions;
//        this.options=options;
//        this.newAnswer = newAnswer
//    }

    override fun getChild(listPosition: Int, expandedListPosition: Int): Any {
        return options[questions[listPosition]]!![expandedListPosition]
    }

    override fun getChildId(listPosition: Int, expandedListPosition: Int): Long {
        return expandedListPosition.toLong()
    }

    override fun getChildView(
        listPosition: Int, expandedListPosition: Int,
        isLastChild: Boolean, convertView: View?, parent: ViewGroup
    ): View {
        var convertView = convertView
        if (convertView == null) {
            val layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.bank_item_row, null)
        }
        val option = convertView!!.findViewById<View>(R.id.firstb) as TextView
        option.text = options[questions[listPosition]]!![expandedListPosition]
//        convertView.setOnClickListener {
//            if (expandedListPosition==0)
//            {
//                Toast.makeText(context,"Option A", Toast.LENGTH_LONG).show()
//
//            }
//            else if (expandedListPosition==1)
//            {
//                Toast.makeText(context,"Option B", Toast.LENGTH_LONG).show()
//            }
//            else if (expandedListPosition==2)
//            {
//                Toast.makeText(context,"Option C", Toast.LENGTH_LONG).show()
//            }
//            else if (expandedListPosition==3)
//            {
//                Toast.makeText(context,"Option D", Toast.LENGTH_LONG).show()
//                option.setTextColor(Color.RED)
//            }
//        }

        convertView.setOnClickListener{
                if (mListener != null) {
                    mListener!!.onChildViewClick(expandedListPosition,option)
                }
            }

        return convertView
    }

    override fun getChildrenCount(listPosition: Int): Int {
        return options[questions[listPosition]]!!.size
    }

    override fun getGroup(listPosition: Int): Any {
        return questions[listPosition]
    }


    override fun getGroupCount(): Int {
        return questions.size
    }


    override fun getGroupId(listPosition: Int): Long {
        return listPosition.toLong()
    }

    override fun getGroupView(
        listPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var convertView = convertView
        val listTitle = getGroup(listPosition) as String
        if (convertView == null) {
            val layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.bank_question_row, null)
        }
        val listTitleTextView = convertView!!.findViewById<View>(R.id.questionb) as TextView
        listTitleTextView.text = listTitle
        return convertView
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun isChildSelectable(listPosition: Int, expandedListPosition: Int): Boolean {
        return true
    }

}