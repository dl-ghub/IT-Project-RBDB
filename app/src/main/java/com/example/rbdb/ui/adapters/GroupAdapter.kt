package com.example.rbdb.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.rbdb.R
import com.example.rbdb.database.model.ListEntity

class GroupAdapter(
    private val data: MutableList<ListEntity>,
    private val groupCardInterface: GroupCardInterface
) : RecyclerView.Adapter<GroupAdapter.GroupViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        return GroupViewHolder(parent)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        holder.onBind(data[position], groupCardInterface)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun swapData(data: List<ListEntity>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    inner class GroupViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.view_holder_group, parent, false)
    ) {
        private val tvGroupName: TextView = itemView.findViewById(R.id.group_name)
        private val groupCard: ConstraintLayout = itemView.findViewById(R.id.group_card)

        fun onBind(groupData: ListEntity, groupCardInterface: GroupCardInterface) {
            tvGroupName.text = groupData.name

            groupCard.setOnClickListener {
                groupCardInterface.onGroupCardClick(absoluteAdapterPosition)
            }
        }
    }
}