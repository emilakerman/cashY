package com.example.expandablerecycleviewkotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cashy.R
import com.example.cashy.Settings

class SettingAdapter(val settingList: List<Settings>) :
    RecyclerView.Adapter<SettingAdapter.SettingVH>() {
    class SettingVH (itemView: View) : RecyclerView.ViewHolder(itemView){

        var codeNameTxt : TextView = itemView.findViewById(R.id.code_name)
        var versionTxt : TextView = itemView.findViewById(R.id.version)
        var apiLevelTxt : TextView = itemView.findViewById(R.id.api_level)
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
        holder.codeNameTxt.text = settings.codeName
        holder.versionTxt.text = settings.version
        holder.apiLevelTxt.text = settings.apiLevel
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