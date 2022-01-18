package com.riac.marketapp.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.riac.marketapp.R
import com.riac.marketapp.domain.model.Item

class ListAdapter(val type: String) : RecyclerView.Adapter<ListAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        if (type == "Horizontal") {
            return ItemViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.horizontal_card,
                    parent,
                    false
                )
            )
        } else {
            return ItemViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.vertical_card,
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Item) -> Unit)? = null

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val entry = differ.currentList[position]
        if (type == "Horizontal") {
            holder.itemView.apply {
                Glide.with(this)
                    .load(entry.thumbnail)
                    .centerCrop()
                    .into(this.findViewById(R.id.hrImg))
                this.findViewById<TextView>(R.id.prTit).text = entry.title.split(",")[0]
                this.findViewById<TextView>(R.id.prPrice).text =
                    "${entry.price} ${entry.currency_id}"
            }
        } else {
            holder.itemView.apply {

                Glide.with(this)
                    .load(entry.thumbnail)
                    .centerCrop()
                    .into(this.findViewById(R.id.vrImg))

                this.findViewById<TextView>(R.id.prTit).text = entry.title.split(",")[0]
                this.findViewById<TextView>(R.id.prPrice).text =
                    "${entry.price} ${entry.currency_id}"
                this.findViewById<TextView>(R.id.prNum).text = entry.id


                setOnClickListener {
                    onItemClickListener?.let {
                        it(entry)
                    }
                }
            }
        }
    }

    fun setOnItemClickListener(listener: (Item) -> Unit) {
        onItemClickListener = listener
    }

}
