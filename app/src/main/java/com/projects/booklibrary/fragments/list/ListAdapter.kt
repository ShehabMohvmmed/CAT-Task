package com.projects.booklibrary.fragments.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.projects.booklibrary.R
import com.projects.booklibrary.model.User
import kotlinx.android.synthetic.main.custom_row.view.*

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var userList = emptyList<User>()

    class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)  {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val customItem = userList[position]
        holder.itemView.textView.text = customItem.id.toString()
        holder.itemView.textView2.text = customItem.firstName
        holder.itemView.textView3.text = customItem.lastName
        holder.itemView.textView4.text = customItem.age.toString()

        holder.itemView.rowlayout.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(customItem)
            holder.itemView.findNavController().navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return userList.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setData(user: List<User>) {
        this.userList = user
        notifyDataSetChanged()
    }
}