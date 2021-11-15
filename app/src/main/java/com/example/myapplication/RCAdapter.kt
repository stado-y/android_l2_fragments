package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class RCAdapter(listArray:ArrayList<ListItem>, context: Context):
    RecyclerView.Adapter<RCAdapter.ViewHolder>() {

    private var RClistArray = listArray
    private var RCcontext = context

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val listImage: ImageView = view.findViewById(R.id.itemImage)
        val listTitle: TextView = view.findViewById(R.id.itemTitle)
        val listText: TextView = view.findViewById(R.id.itemText)


        fun bind(listitem: ListItem, context: Context) {

            listImage.setImageResource(listitem.item_image_id)
            listTitle.text = listitem.item_title
            listText.text = listitem.item_text

            itemView.setOnClickListener {

                Toast.makeText(context, "${ listTitle.text }", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(RCcontext)
        return ViewHolder(inflater.inflate(R.layout.item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val listItem = RClistArray[position]
        holder.bind(listItem, RCcontext)
    }

    override fun getItemCount(): Int {

        return RClistArray.size
    }

    fun updateAdapter(listArray: ArrayList<ListItem>) {

        RClistArray.clear()
        RClistArray.addAll(listArray)
        notifyDataSetChanged()
    }
}

