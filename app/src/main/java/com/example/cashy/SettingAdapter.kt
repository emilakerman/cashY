package com.example.expandablerecycleviewkotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cashy.R
import com.example.cashy.Settings

class SettingAdapter(val context: Context, private val settingList: List<Settings>) :
    RecyclerView.Adapter<SettingAdapter.SettingVH>() {

    class SettingVH (itemView: View) : RecyclerView.ViewHolder(itemView) {

        var titleTxt : TextView = itemView.findViewById(R.id.title_name)
        var subheadingTxt : TextView = itemView.findViewById(R.id.subheading)
        var sectionTxt : TextView = itemView.findViewById(R.id.section)
        var descriptionTxt : TextView = itemView.findViewById(R.id.description)
        var linearLayout : LinearLayout = itemView.findViewById(R.id.linearLayout)
        var expandablelayout : RelativeLayout = itemView.findViewById(R.id.expandable_layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingVH {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)

        return SettingVH(view)
    }


    override fun getItemCount(): Int {
        return settingList.size
    }

    override fun onBindViewHolder(holder: SettingVH, position: Int) {

        val settings : Settings = settingList[position]

        holder.titleTxt.text = settings.title
        holder.subheadingTxt.text = settings.subheading
        holder.sectionTxt.text = settings.section
        holder.descriptionTxt.text = settings.description

        val isExpandable : Boolean = settingList[position].expandable
        holder.expandablelayout.visibility = if (isExpandable) View.VISIBLE else View.GONE

        holder.linearLayout.setOnClickListener {
            val settings = settingList[position]
            settings.expandable = !settings.expandable
            notifyItemChanged(position)
        }
    }
}